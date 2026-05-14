package com.aozelce.controller;

import com.aozelce.util.AuthUtils;
import com.aozelce.entity.Recommendation;
import com.aozelce.entity.Source;
import com.aozelce.entity.User;
import com.aozelce.persistence.GenericDao;
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

import static com.aozelce.util.AuthUtils.getAuthenticatedUser;

/**
 * Servlet for editing recommendations.
 * <p>
 * Handles GET requests to show the edit form for a recommendation and POST requests
 * to update the recommendation. Only authenticated users can access this servlet,
 * and users can only edit their own recommendations.
 * </p>
 * @author aozelce
 */
@WebServlet("/editRecommendation")
public class EditRecommendation extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Handles GET requests to load the edit recommendation form.
     *
     * This method retrieves the authenticated user from the session and ensures the user is authorized
     * to edit the specified recommendation. If the user is not authenticated or authorized, appropriate
     * error responses are sent. The method fetches the recommendation and the user's sources from the
     * database and forwards the data to the edit form JSP for rendering.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for sending the response
     * @throws ServletException if an input or output error occurs while handling the request
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the authenticated user from the session, redirect unauthenticated users
        User user = getAuthenticatedUser(request, response);
        Recommendation rec = getRecommendationFromRequest(request, response);
        if (rec == null) return;

        // Ownership check — ensure the recommendation belongs to the authenticated user
        // Prevents one user from loading the edit form for another user's recommendation
        if (rec.getUser() == null || rec.getUser().getId() != user.getId()) {
            logger.warn("User {} attempted to access edit page for recommendation {} not owned by them",
                    user.getId(),rec.getId());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorized to edit this recommendation");
            return;
        }

        // Fetch only this user's sources to populate the source dropdown on the edit form
        GenericDao<Source> sourceDao = new GenericDao<>(Source.class);
        List<Source> sources = sourceDao.getByPropertyEqual("user", user);
        // Pass the recommendation and sources to the JSP for rendering
        request.setAttribute("recommendation", rec);
        request.setAttribute("sources", sources);

        // Forward to the edit form JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/editRecommendation.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles POST requests to update an existing recommendation.
     *
     * This method retrieves the authenticated user from the session and ensures the user is authorized
     * to edit the specified recommendation. It processes the submitted form data, updates the recommendation
     * in the database, and redirects the user to the recommendations list. If the user is not authenticated
     * or authorized, appropriate error responses are sent.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for sending the response
     * @throws IOException if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Get the authenticated user from the session, redirect unauthenticated users
        User user = getAuthenticatedUser(request, response);

        Recommendation rec = getRecommendationFromRequest(request, response);
        if (rec == null) return;

        // Ownership check — prevent users from editing recommendations that don't belong to them
        if (rec.getUser() == null || rec.getUser().getId() != user.getId()) {
            logger.warn("User {} attempted to edit recommendation {} not owned by them",
                    user.getId(), rec.getId());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorized to edit this recommendation");
            return;
        }

        // Read the editable fields from the submitted form
        String notes = request.getParameter("notes");
        String isWatched = request.getParameter("isWatched");
        String sourceName = request.getParameter("sourceName");

        // Trim notes if provided, otherwise set to null
        rec.setNotes(notes != null ? notes.trim() : null);
        // Checkbox sends "on" when checked, null when unchecked
        rec.setWatched("on".equals(isWatched));

        if (sourceName != null && !sourceName.trim().isEmpty()) {
            GenericDao<Source> sourceDao = new GenericDao<>(Source.class);
            // Fetch only this user's sources to avoid matching another user's source with the same name
            List<Source> userSources = sourceDao.getByPropertyEqual("user", user);
            Source source = null;
            // Look for an existing source with the same name
            for (Source s : userSources) {
                if (sourceName.trim().equals(s.getName())) {
                    source = s;
                    break;
                }
            }
            if (source == null) {
                // No match found — create a new source for this user
                source = new Source();
                source.setUser(user);
                source.setName(sourceName.trim());
                int sourceId = sourceDao.insert(source);
                source.setId(sourceId);
                logger.info("Created new source '{}' for user {}", sourceName.trim(), user.getId());
            }
            rec.setSource(source);
        } else {
            // Source field was left empty — clear the source association
            rec.setSource(null);
        }

        // Persist the updated recommendation and redirect to the list
        GenericDao<Recommendation> recDao = new GenericDao<>(Recommendation.class);
        recDao.saveOrUpdate(rec);
        logger.info("User {} updated recommendation {}", user.getId(), rec.getId());
        response.sendRedirect("recommendations");
    }


    /**
     * Helper method to retrieve a recommendation from the database based on
     * the request parameters.
     * <p>
     * This method validates the recommendation ID provided in the request, fetches the
     * corresponding recommendation from the database, and ensures it exists. If the ID
     * is invalid or the recommendation is not found, appropriate error responses are sent.
     *
     * @param request  the HttpServletRequest object containing the client's request
     * @param response the HttpServletResponse object for sending the response
     * @return the Recommendation object if found, or null if an error occurs
     * @throws IOException if an input or output error is detected when handling the request
     */
    private Recommendation getRecommendationFromRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Redirect unauthenticated users
        User user = AuthUtils.getAuthenticatedUser(request, response);
        if (user == null) {
            return null;
        }

        // Read the recommendation ID submitted by the form
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing recommendation ID");
            return null;
        }
        // Parse the ID to an integer
        int recId;
        try {
            recId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            logger.warn("Invalid recommendation ID format: {}", idParam);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid recommendation ID");
            return null;
        }

        // Fetch the recommendation from the database
        GenericDao<Recommendation> recDao = new GenericDao<>(Recommendation.class);
        Recommendation rec = recDao.getById(recId);
        // Verify the recommendation exists
        if (rec == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recommendation not found");
            return null;
        }
        // Return the recommendation object for further processing
        return rec;
    }
}