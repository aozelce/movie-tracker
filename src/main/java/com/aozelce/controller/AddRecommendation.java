package com.aozelce.controller;

import com.aozelce.auth.AuthRedirector;
import com.aozelce.auth.UserSessionHelper;
import com.aozelce.entity.Media;
import com.aozelce.entity.Source;
import com.aozelce.entity.User;
import com.aozelce.persistence.GenericDao;
import com.aozelce.persistence.TmdbDao;
import com.aozelce.service.MediaService;
import com.aozelce.service.RecommendationService;
import com.aozelce.service.TmdbGenreService;
import com.themoviedb.Movie;
import com.themoviedb.ResultsItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Servlet that handles the two recommendation-addition workflows: the TMDB
 * search path (search -> select -> confirm -> save) and the manual path (fill
 * form -> save).
 * <p>
 * All endpoints require an authenticated session. Unauthenticated requests are
 * redirected by AuthRedirector.
 * <p>
 * GET actions (via "page" parameter): tmdb-search - renders the TMDB search
 * form (default) manual -renders the manual entry form
 * <p>
 * POST actions (via "action" parameter): search-tmdb - runs a TMDB title search
 * and shows results select-tmdb - processes a selected result; shows
 * confirmation on first pass, saves the recommendation on final submit
 * add-manual - validates and persists a manually entered recommendation
 *
 * @author aozelce
 */
@WebServlet("/addRecommendation")
public class AddRecommendation extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private final MediaService mediaService = new MediaService(this);
    private final RecommendationService recommendationService = new RecommendationService(this);

    private TmdbDao tmdbDao;
    private TmdbGenreService tmdbGenreService;

    /**
     * Loads servlet-scoped dependencies from the ServletContext.
     * Both TmdbDao and TmdbGenreService must be placed in application scope
     * before the first request arrives (e.g., by a context listener).
     *
     * @throws ServletException if the superclass init() fails
     */
    public void init() throws ServletException {
        tmdbDao = (TmdbDao) getServletContext().getAttribute("tmdbDao");
        tmdbGenreService = (TmdbGenreService) getServletContext().getAttribute("genreService");
    }

    /**
     * Renders the recommendation entry page selected by the "page" query parameter.
     * Defaults to the TMDB search page if the parameter is absent.
     * Returns 400 for unknown values and 500 if an unexpected error occurs.
     *
     * @param request  the HTTP request; must carry a valid user session
     * @param response the HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthRedirector.redirectIfUnauthenticated(request, response)) {
            return;
        }

        String page = request.getParameter("page");

        // Defaults to the TMDB search page if the parameter is absent or unrecognized.
        if (page == null || page.isEmpty()) {
            page = "tmdb-search";
        }

        // Route to the appropriate page based on the 'page' query parameter.
        if ("manual".equals(page)) {
            showManualPage(request, response);
        } else {
            showTmdbSearchPage(request, response);
        }
    }

    /**
     * Processes the recommendation creation step identified by the "action" parameter.
     * Returns 400 for missing or unknown actions and 500 if an unexpected error occurs.
     *
     * @param request  the HTTP request; must carry a valid user session
     * @param response the HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!AuthRedirector.redirectIfUnauthenticated(request, response)) {
            return;
        }

        // Determine which recommendation workflow step to execute based on
        // the 'action' parameter.
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "search-tmdb":
                    handleTmdbSearch(request, response);
                    break;
                case "select-tmdb":
                    handleSelectTmdb(request, response);
                    break;
                case "add-manual":
                    handleAddManual(request, response);
                    break;
                default:
                    logger.warn("Unknown action: {}", action);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
            }
        } catch (Exception e) {
            logger.error("Error processing recommendation action", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    /**
     * Forwards to the TMDB search form JSP.
     */
    private void showTmdbSearchPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/searchTmdb.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Forwards to the manual entry form JSP.
     */
    private void showManualPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get logged-in user from session
        User user = UserSessionHelper.getUserFromSession(request);

        if (user != null) {
            // Fetch sources associated with this user
            List<Source> sources = user.getSources();
            request.setAttribute("sources", sources);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/addManually.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Queries TMDB with the submitted title and forwards enriched results to searchResults.jsp.
     */
    private void handleTmdbSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");

        if (query == null || query.trim().isEmpty()) {
            logger.warn("Search submitted with empty query");
            request.setAttribute("message", "Please enter a movie or TV show title.");
            request.getRequestDispatcher("/searchTmdb.jsp").forward(request, response);
            return;
        }

        query = query.trim();

        Movie movieResults = tmdbDao.searchMovie(query);
        request.setAttribute("searchQuery", query);

        if (movieResults == null || movieResults.getResults() == null || movieResults.getResults().isEmpty()) {
            showManualPage(request, response);
            return;

        } else {
            for (ResultsItem result : movieResults.getResults()) {
                // Fetch genre names from the GenreIds and set the genres
                // property for display in the JSP
                String genreNames = tmdbGenreService.getGenreNames(result.getGenreIds());
                result.setGenres(genreNames);
            }
            request.setAttribute("results", movieResults.getResults());
            request.getSession().setAttribute("tmdbResults", movieResults.getResults());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/searchResults.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles TMDB result selection in two passes.
     *
     * Pass 1 - confirmation: when "sourceName" and "notes" are both absent, the selected
     * item is looked up from the session cache and forwarded to confirmRecommendation.jsp.
     *
     * Pass 2 - final submit: when either "sourceName" or "notes" is present, the Media
     * record is persisted (or reused if it already exists), the recommendation is saved,
     * and the user is redirected to /recommendations.
     *
     * @param request  must contain "tmdbId"; requires a valid "tmdbResults" session attribute
     * @param response the HTTP response
     * @throws ServletException if forwarding fails
     * @throws IOException      if an I/O error occurs
     */
    private void handleSelectTmdb(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tmdbIdStr = request.getParameter("tmdbId");

        // Validate tmdbId is present and numeric before parsing
        if (tmdbIdStr == null || tmdbIdStr.trim().isEmpty()) {
            logger.warn("select-tmdb action called with missing tmdbId");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing TMDB ID");
            return;
        }

        // Parse tmdbId and handle invalid formats
        int tmdbId;
        try {
            tmdbId = Integer.parseInt(tmdbIdStr);
        } catch (NumberFormatException e) {
            logger.warn("Invalid TMDB ID format: {}", tmdbIdStr);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid TMDB ID");
            return;
        }

        // Log the selected TMDB ID for debugging
        logger.info("User selected/confirmed TMDB ID: {}", tmdbId);

        // Guard against expired or missing session cache — user may have navigated directly
        List<ResultsItem> results = (List<ResultsItem>) request.getSession().getAttribute("tmdbResults");
        if (results == null || results.isEmpty()) {
            logger.warn("tmdbResults not found in session for tmdbId: {}", tmdbId);
            request.setAttribute("message", "Session expired. Please search again.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/searchTmdb.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Find the selected TMDB result out of all in the session cache
        // Since the search returns many results
        ResultsItem selected = null;
        for (ResultsItem item : results) {
            if (item.getId() == tmdbId) {
                selected = item;
                break;
            }
        }

        // Handle case where the selected TMDB ID is not found in the session cache
        if (selected == null) {
            logger.warn("TMDB ID {} not found in session results", tmdbId);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Selected item not found. Please search again.");
            return;
        }

        // Check if the user confirmed the selection
        String confirmed = request.getParameter("confirmed");
        if ("true".equals(confirmed)) {
            // Pass 2: persist the media and recommendation, then redirect to
            // the  recommendations page
            Media media = mediaService.createMediaFromTmdbResult(selected);
            if (media == null) {
                throw new RuntimeException("Failed to create Media from TMDB selection");
            }
            media = mediaService.findOrCreateMedia(tmdbId, media);
            recommendationService.createRecommendation(request, media);
            logger.info("Recommendation saved for tmdbId: {}", tmdbId);
            response.sendRedirect("recommendations");
            return;
        }

        // Pass 1: build a transient Media object to pre-populate the confirmation form
        Media media = mediaService.createMediaFromTmdbResult(selected);
        request.setAttribute("media", media);

        // Load user's sources for the datalist
        User user = UserSessionHelper.getUserFromSession(request);
        if (user != null) {
            request.setAttribute("sources", user.getSources());
        }

        // Forward to the confirmation page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmRecommendation.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Validates form input, builds and persists a Media record, then saves the recommendation.
     * "title" and "mediaType" are required; all other fields are optional.
     */
    private void handleAddManual(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String title = request.getParameter("title");
        String mediaType = request.getParameter("mediaType");

        if (title == null || title.trim().isEmpty() || mediaType == null || mediaType.trim().isEmpty()) {
            request.setAttribute("message", "Title and Media Type are required.");
            showManualPage(request, response);
            return;
        }

        logger.info("Creating manual recommendation: title={}, mediaType={}", title, mediaType);

        // Create Media entry
        Media media = new Media();
        // Set properties
        media.setTitle(title.trim());
        media.setMediaType(mediaType.trim());
        // Assign a unique negative tmdbId for manual entries to avoid
        // duplicate key errors
        //Reference : https://stackoverflow.com/questions/34607427/creating
        // -unique-request-id-for-each-request-using-timemillis-method-in-servlet
        media.setTmdbId((int)(-1 * System.currentTimeMillis() / 1000));

        String yearStr = request.getParameter("year");
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                media.setYear(Integer.parseInt(yearStr));
            } catch (NumberFormatException e) {
                logger.warn("Invalid year format: {}", yearStr);
            }
        }

        media.setOverview(request.getParameter("overview"));
        media.setPosterPath(request.getParameter("posterPath"));
        media.setGenres(request.getParameter("genres"));

        GenericDao<Media> mediaDao = new GenericDao<>(Media.class);
        int mediaId = mediaDao.insert(media);
        media.setId(mediaId);
        // Create a Recommendation entry with the media
        recommendationService.createRecommendation(request, media);
        response.sendRedirect("recommendations");
    }
}