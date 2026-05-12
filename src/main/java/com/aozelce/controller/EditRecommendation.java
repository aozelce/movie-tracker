package com.aozelce.controller;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * The type Edit recommendation.
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

        // Get the existing session without creating a new one — avoids unnecessary session creation
        HttpSession session = request.getSession(false);
        // Extract the authenticated user from the session, or null if no session exists
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Redirect unauthenticated users to the login page
        if (user == null) {
            response.sendRedirect("logIn");
            return;
        }

        // Read the recommendation ID from the query string
        String idParam = request.getParameter("id");
        // Reject the request if the ID is missing or blank
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing recommendation ID");
            return;
        }

        int recId;
        try {
            // Parse the ID — will throw if the value is not a valid integer
            recId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            logger.warn("Invalid recommendation ID format: {}", idParam);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid recommendation ID");
            return;
        }

        // Fetch the recommendation from the database by its ID
        GenericDao<Recommendation> recDao = new GenericDao<>(Recommendation.class);
        Recommendation rec = recDao.getById(recId);

        // Return 404 if no recommendation exists with that ID
        if (rec == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recommendation not found");
            return;
        }

        // Ownership check — ensure the recommendation belongs to the authenticated user
        // Prevents one user from loading the edit form for another user's recommendation
        if (rec.getUser() == null || rec.getUser().getId() != user.getId()) {
            logger.warn("User {} attempted to access edit page for recommendation {} not owned by them",
                    user.getId(), recId);
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

        // Get the existing session without creating a new one
        HttpSession session = request.getSession(false);
        // Extract the authenticated user from the session, or null if no session exists
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Redirect unauthenticated users to the login page
        if (user == null) {
            response.sendRedirect("logIn");
            return;
        }

        // Read the recommendation ID submitted by the form
        String idParam = request.getParameter("id");
        // Reject the request if the ID is missing or blank
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing recommendation ID");
            return;
        }

        int recId;
        try {
            // Parse the ID — will throw if the value is not a valid integer
            recId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            logger.warn("Invalid recommendation ID format in POST: {}", idParam);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid recommendation ID");
            return;
        }

        // Fetch the recommendation from the database by its ID
        GenericDao<Recommendation> recDao = new GenericDao<>(Recommendation.class);
        Recommendation rec = recDao.getById(recId);

        // Return 404 if no recommendation exists with that ID
        if (rec == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recommendation not found");
            return;
        }

        // Ownership check — prevent users from editing recommendations that don't belong to them
        if (rec.getUser() == null || rec.getUser().getId() != user.getId()) {
            logger.warn("User {} attempted to edit recommendation {} not owned by them",
                    user.getId(), recId);
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
        recDao.saveOrUpdate(rec);
        logger.info("User {} updated recommendation {}", user.getId(), recId);
        response.sendRedirect("recommendations");
    }
}