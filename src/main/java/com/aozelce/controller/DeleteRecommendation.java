package com.aozelce.controller;

import com.aozelce.entity.Recommendation;
import com.aozelce.entity.User;
import com.aozelce.persistence.GenericDao;
import com.aozelce.util.AuthUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet responsible for deleting a recommendation. Requires authentication
 * and verifies user ownership.
 * <p>
 * URL: /deleteRecommendation?id={recommendationId}
 */
@WebServlet("/deleteRecommendation")
public class DeleteRecommendation extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Validate user is authenticated
        User user = AuthUtils.getAuthenticatedUser(request, response);
        if (user == null) {
            return;   // Already redirected or error sent
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

