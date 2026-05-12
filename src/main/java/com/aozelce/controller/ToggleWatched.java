package com.aozelce.controller;

import com.aozelce.entity.Recommendation;
import com.aozelce.entity.User;
import com.aozelce.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.aozelce.util.AuthUtils.getAuthenticatedUser;

/**
 * Servlet that handles AJAX requests to toggle the 'watched' state of a
 * recommendation. Expects 'id' and 'watched' parameters in the POST request and
 * updates the corresponding recommendation.
 *
 * @author aozelce
 */
@WebServlet("/toggleWatched")
public class ToggleWatched extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles POST requests to toggle the watched state of a recommendation.
     *
     * @param request  the HttpServletRequest containing 'id' and 'watched' parameters
     * @param response the HttpServletResponse returning JSON with success status
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Get the authenticated user from the session
        User user = getAuthenticatedUser (request, response);

        // Reject unauthenticated requests — return JSON since this is an AJAX endpoint
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Unauthorized\"}");
            return;
        }

        // Read the recommendation ID and desired watched state from the request
        String idParam = request.getParameter("id");
        String watchedParam = request.getParameter("watched");

        // Both parameters are required — reject if either is missing
        if (idParam == null || watchedParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Missing required parameters\"}");
            return;
        }

        int id;
        try {
            // Parse the ID — will throw if the value is not a valid integer
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            logger.warn("Invalid recommendation ID format in toggleWatched: {}", idParam);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Invalid recommendation ID\"}");
            return;
        }

        // Parse the watched boolean — Boolean.parseBoolean returns false for any non-"true" string
        boolean watched = Boolean.parseBoolean(watchedParam);

        // Fetch the recommendation from the database by its ID
        GenericDao<Recommendation> dao = new GenericDao<>(Recommendation.class);
        Recommendation rec = dao.getById(id);

        // Return 404 if no recommendation exists with that ID
        if (rec == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Recommendation not found\"}");
            return;
        }

        // Ownership check — prevent users from toggling recommendations that don't belong to them
        if (rec.getUser() == null || rec.getUser().getId() != user.getId()) {
            logger.warn("User {} attempted to toggle recommendation {} not owned by them",
                    user.getId(), id);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Not authorized\"}");
            return;
        }

        // Update the watched state and persist the change
        rec.setWatched(watched);
        dao.saveOrUpdate(rec);
        logger.info("User {} toggled recommendation {} watched={}", user.getId(), id, watched);

        // Return a JSON success response with the new watched state
        response.setContentType("application/json");
        response.getWriter().write("{\"success\":true,\"watched\":" + watched + "}");
    }
}