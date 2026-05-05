package com.aozelce.controller;

import com.aozelce.entity.Recommendation;
import com.aozelce.persistence.GenericDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that handles AJAX requests to toggle the 'watched' state of a recommendation.
 * Expects 'id' and 'watched' parameters in the POST request and updates the corresponding recommendation.
 *
 * @author aozelce
 */
@WebServlet("/toggleWatched")
public class ToggleWatched extends HttpServlet {
    /**
     * Handles POST requests to toggle the watched state of a recommendation.
     *
     * @param request  the HttpServletRequest containing 'id' and 'watched' parameters
     * @param response the HttpServletResponse returning JSON with success status
     * @throws IOException if an input or output error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the recommendation ID and watched state from the request parameters
        String idParam = request.getParameter("id");
        String watchedParam = request.getParameter("watched");
        // Validate parameters
        if (idParam == null || watchedParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        // Parse the ID and watched state
        int id = Integer.parseInt(idParam);
        boolean watched = Boolean.parseBoolean(watchedParam);
        // Retrieve the recommendation from the database
        GenericDao<Recommendation> dao = new GenericDao<>(Recommendation.class);
        Recommendation rec = dao.getById(id);
        if (rec == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // Update the watched state and persist the change
        rec.setWatched(watched);
        dao.saveOrUpdate(rec);
        // Return a JSON response indicating success
        response.setContentType("application/json");
        response.getWriter().write("{\"success\":true,\"watched\":" + watched + "}");
    }
}
