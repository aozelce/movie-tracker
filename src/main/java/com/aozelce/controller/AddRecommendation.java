package com.aozelce.controller;

import com.aozelce.auth.AuthRedirector;
import com.aozelce.entity.User;
import com.aozelce.persistence.GenericDao;
import com.aozelce.persistence.TmdbDao;
import com.aozelce.service.TmdbGenreService;
import com.themoviedb.Movie;
import com.themoviedb.ResultsItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import com.aozelce.entity.Media;
import com.aozelce.entity.Recommendation;
import java.util.List;
import com.aozelce.entity.Source;
import com.aozelce.auth.UserSessionHelper;



/**
 * Servlet responsible for handling recommendation addition flows.
 * 
 * Two separate workflows:
 * 1. TMDB Search Path: Search TMDB -> Select Result -> Confirm & Add
 * 2. Manual Path: Directly add movie not found in TMDB
 *
 * @author aozelce
 */
@WebServlet("/addRecommendation")
public class AddRecommendation extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private TmdbDao tmdbDao;
    private TmdbGenreService tmdbGenreService;

    public void init() throws ServletException {
        // Retrieve the movie service from the app scope
        tmdbDao = (TmdbDao) getServletContext().getAttribute("tmdbDao");
        // Retrieve the genre service from the app scope
        tmdbGenreService = (TmdbGenreService) getServletContext().getAttribute(
                "genreService");
    }

    /**
     * Handles GET requests to display recommendation entry pages.
     * Query parameters:
     *   - page: "tmdb-search" (search TMDB), "manual" (manual entry)
     *
     * @param request  the HTTP request object
     * @param response the HTTP response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if the user is authenticated. If not, the method handles redirection and returns false.
        if (!AuthRedirector.redirectIfUnauthenticated(request, response)) {
            return; // Exit if the user is not authenticated (redirected already)
        }

        String page = request.getParameter("page");
        if (page == null || page.isEmpty()) {
            page = "tmdb-search";
        }

        // The 'page' parameter determines which add recommendation flow the user wants:
        // This switch statement routes the request to the correct JSP based on the page parameter.
        // If the parameter is missing or unrecognized, it defaults to TMDB search or returns a 400 error.
        // This logic ensures the servlet can flexibly serve different UI pages and handle errors gracefully.
        // The try-catch ensures any error in routing is logged and the user is forwarded to a friendly error page.
        try {
            switch (page) {
                case "tmdb-search":
                    showTmdbSearchPage(request, response);
                    break;
                case "manual":
                    showManualPage(request, response);
                    break;
                default:
                    logger.warn("Unknown page: {}", page);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown page");
            }
        } catch (Exception e) {
            logger.error("Error displaying recommendation page", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error: " + e.getMessage());
        }
    }

    /**
     * Handles POST requests for creating recommendations.
     * Parameters:
     *   - action: "search-tmdb", "select-tmdb", or "add-manual"
     *
     * @param request  the HTTP request object
     * @param response the HTTP response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if the user is authenticated. If not, the method handles redirection and returns false.
        if (!AuthRedirector.redirectIfUnauthenticated(request, response)) {
            return; // Exit if the user is not authenticated (redirected already)
        }

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
     * Displays the TMDB search page.
     */
    private void showTmdbSearchPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = UserSessionHelper.getUserFromSession(request);
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/searchTmdb.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Displays the manual entry page.
     */
    private void showManualPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = UserSessionHelper.getUserFromSession(request);
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/addManually.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles TMDB search action.
     * POST parameters:
     *   query: search query for TMDB
     */
    private void handleTmdbSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = UserSessionHelper.getUserFromSession(request);
        String query = request.getParameter("query");
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query is required");
        }
        query = query.trim();
        Movie movieResults = tmdbDao.searchMovie(query);
        request.setAttribute("searchQuery", query);
        request.setAttribute("user", user);
        if (movieResults == null || movieResults.getResults() == null || movieResults.getResults().isEmpty()) {
            request.setAttribute("message", "No results found for: " + query);
        } else {
            // Map genre IDs to names for each result using dynamic TMDB API
            for (ResultsItem result : movieResults.getResults()) {
                String genreNames = tmdbGenreService.getGenreNames(result.getGenreIds());
                result.setGenres(genreNames);
            }
            request.setAttribute("results", movieResults.getResults());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/searchResults.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles TMDB result selection - either shows confirmation page or saves recommendation.
     * POST parameters:
     *   - tmdbId: TMDB ID of selected movie
     *   - title: Movie title
     *   - mediaType: Type (movie/tv)
     *   - year: Release year
     *   - posterPath: Poster URL
     *   - overview: Description
     *   - genres: Genres
     *   - sourceName: Source (optional, when saving)
     *   - notes: Notes (optional, when saving)
     *   - isWatched: Already watched (optional, when saving)
     */
    private void handleSelectTmdb(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = UserSessionHelper.getUserFromSession(request);

        String tmdbIdStr = request.getParameter("tmdbId");
        if (tmdbIdStr == null || tmdbIdStr.isEmpty()) {
            throw new IllegalArgumentException("tmdbId is required");
        }

        int tmdbId = Integer.parseInt(tmdbIdStr);
        logger.info("User selected/confirmed TMDB ID: {}", tmdbId);

        // Check if this is the final submit (has sourceName/notes) or initial selection
        String sourceName = request.getParameter("sourceName");
        String notes = request.getParameter("notes");

        // If sourceName or notes are present, this is a final submission - save the recommendation
        if (sourceName != null || notes != null) {
            // Create or retrieve Media from TMDB data
            Media media = createMediaFromRequest(request, tmdbId);
            if (media == null) {
                throw new RuntimeException("Failed to create Media from TMDB selection");
            }
            // Check if media already exists in database
            GenericDao<Media> mediaDao = new GenericDao<>(Media.class);
            List<Media> existingMedia = mediaDao.getByPropertyEqual("tmdbId", tmdbId);
            if (existingMedia != null && !existingMedia.isEmpty()) {
                media = existingMedia.get(0);
            } else {
                int mediaId = mediaDao.insert(media);
                media.setId(mediaId);
            }
            // Create recommendation
            createRecommendation(request, user, media);
            // Redirect to recommendations list
            response.sendRedirect("recommendations");
            return;
        }

        // This is initial selection - show confirmation page
        Media media = createMediaFromRequest(request, tmdbId);
        request.setAttribute("media", media);
        request.setAttribute("user", user);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/confirmRecommendation.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles manual recommendation creation.
     * POST parameters:
     *   - title: Movie title (required)
     *   - mediaType: Type (required)
     *   - year: Release year (optional)
     *   - overview: Description (optional)
     *   - genres: Genres (optional)
     *   - posterPath: Poster URL (optional)
     *   - sourceName: Source/who recommended (optional)
     *   - notes: Recommendation notes (optional)
     *   - isWatched: Already watched (optional)
     */
    private void handleAddManual(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = UserSessionHelper.getUserFromSession(request);

        String title = request.getParameter("title");
        String mediaType = request.getParameter("mediaType");

        if (title == null || title.trim().isEmpty() || mediaType == null || mediaType.trim().isEmpty()) {
            throw new IllegalArgumentException("Title and Media Type are required");
        }

        logger.info("Creating manual recommendation: title={}, mediaType={}", title, mediaType);

        // Create Media entry
        Media media = new Media();
        media.setTitle(title.trim());
        media.setMediaType(mediaType.trim());
        // Assign a unique negative tmdbId for manual entries to avoid
        // duplicate key errors
        //Reference : https://stackoverflow.com/questions/34607427/creating
        // -unique-request-id-for-each-request-using-timemillis-method-in-servlet
        media.setTmdbId((int)(-1 * System.currentTimeMillis() / 1000));

        // Optional fields
        String yearStr = request.getParameter("year");
        if (yearStr != null && !yearStr.isEmpty()) {
            try {
                media.setYear(Integer.parseInt(yearStr));
            } catch (NumberFormatException e) {
                logger.warn("Invalid year format: {}", yearStr);
            }
        }

        media.setOverview(request.getParameter("overview"));
        media.setGenres(request.getParameter("genres"));
        media.setPosterPath(request.getParameter("posterPath"));

        // Insert media
        GenericDao<Media> mediaDao = new GenericDao<>(Media.class);
        int mediaId = mediaDao.insert(media);
        media.setId(mediaId);

        // Create recommendation
        createRecommendation(request, user, media);

        // Redirect to recommendations list
        response.sendRedirect("recommendations");
    }

    /**
     * Creates a Media object from request parameters.
     *
     * @param request the HTTP request containing media data
     * @param tmdbId the TMDB ID
     * @return the created Media object
     */
    private Media createMediaFromRequest(HttpServletRequest request, int tmdbId) {
        try {
            String title = request.getParameter("title");
            String mediaType = request.getParameter("mediaType");
            String yearStr = request.getParameter("year");
            String posterPath = request.getParameter("posterPath");
            String overview = request.getParameter("overview");
            String genres = request.getParameter("genres");

            Media media = new Media();
            media.setTmdbId(tmdbId);
            media.setTitle(title);
            media.setMediaType(mediaType);
            media.setPosterPath(posterPath);
            media.setOverview(overview);
            media.setGenres(genres);

            if (yearStr != null && !yearStr.isEmpty()) {
                try {
                    media.setYear(Integer.parseInt(yearStr));
                } catch (NumberFormatException e) {
                    logger.warn("Invalid year format: {}", yearStr);
                }
            }

            return media;
        } catch (Exception e) {
            logger.error("Error creating Media from request", e);
            return null;
        }
    }

    /**
     * Helper method to create a Recommendation entity and persist it.
     *
     * @param request the HTTP request containing recommendation details
     * @param user the user who is creating the recommendation
     * @param media the media object for the recommendation
     */
    private void createRecommendation(HttpServletRequest request, User user, Media media) {
        try {
            Recommendation recommendation = new Recommendation();
            recommendation.setUser(user);
            recommendation.setMedia(media);

            // Optional source - handles text input for new source creation
            String sourceName = request.getParameter("sourceName");
            if (sourceName != null && !sourceName.trim().isEmpty()) {
                sourceName = sourceName.trim();
                GenericDao<com.aozelce.entity.Source> sourceDao = new GenericDao<>(com.aozelce.entity.Source.class);
                
                // Try to find existing source with this name for the user
                List<Source> existingSources = sourceDao.getByPropertyEqual("name", sourceName);
                com.aozelce.entity.Source source = null;
                
                if (existingSources != null && !existingSources.isEmpty()) {
                    // Use existing source
                    source = existingSources.get(0);
                    logger.info("Using existing source: {}", sourceName);
                } else {
                    // Create new source for this user
                    source = new com.aozelce.entity.Source();
                    source.setUser(user);
                    source.setName(sourceName);
                    int sourceId = sourceDao.insert(source);
                    source.setId(sourceId);
                    logger.info("Created new source: {} for user: {}", sourceName, user.getId());
                }
                
                recommendation.setSource(source);
            }

            // Optional notes
            String notes = request.getParameter("notes");
            if (notes != null && !notes.isEmpty()) {
                recommendation.setNotes(notes.trim());
            }

            // Optional watched status
            String isWatched = request.getParameter("isWatched");
            recommendation.setWatched("on".equals(isWatched));

            // Save recommendation
            GenericDao<Recommendation> recDao = new GenericDao<>(Recommendation.class);
            recDao.insert(recommendation);
            
        } catch (Exception e) {
            logger.error("Error creating recommendation", e);
            throw new RuntimeException("Failed to create recommendation", e);
        }
    }
}

