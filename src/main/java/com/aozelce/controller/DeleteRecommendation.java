package com.aozelce.controller;

import com.aozelce.entity.Recommendation;
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

/**
 * Servlet responsible for deleting a recommendation.
 * Requires authentication and verifies user ownership.
 *
 * URL: /deleteRecommendation?id={recommendationId}
 */
@WebServlet("/deleteRecommendation")
public class DeleteRecommendation extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Validate user is authenticated
        HttpSession session = request.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }
        if (user == null) {
            String loginUrl = (String) getServletContext().getAttribute("loginURL");
            if (loginUrl != null) {
                response.sendRedirect(loginUrl);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authenticated");
            }
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing recommendation ID");
            return;
        }
        int recId;
        try {
            recId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid recommendation ID");
            return;
        }

        GenericDao<Recommendation> recDao = new GenericDao<>(Recommendation.class);
        Recommendation rec = recDao.getById(recId);
        if (rec == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recommendation not found");
            return;
        }
        // Ensure the recommendation belongs to the authenticated user
        if (rec.getUser() == null || rec.getUser().getId() != user.getId()) {
            logger.warn("User {} attempted to delete recommendation {} not owned by them", user.getId(), recId);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Not authorized to delete this recommendation");
            return;
        }
        // Delete the recommendation
        recDao.delete(rec);
        logger.info("User {} deleted recommendation {}", user.getId(), recId);
        // Redirect to recommendations list
        response.sendRedirect("recommendations");
    }
}

