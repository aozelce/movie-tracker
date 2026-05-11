package com.aozelce.controller;

import com.aozelce.entity.Recommendation;
import com.aozelce.entity.User;
import com.aozelce.persistence.GenericDao;
import com.aozelce.util.AuthUtils;
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
 * Servlet responsible for handling user recommendation requests. Retrieves and
 * displays the list of recommendations for the authenticated user.
 *
 * @author aozelce
 */
@WebServlet("/recommendations")
public class RecommendationServlet extends HttpServlet {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles GET requests to display the user's recommendations.
     * Validates user session, retrieves recommendations from the database,
     * and forwards to the recommendations JSP page.
     *
     * @param request  the HTTP request object
     * @param response the HTTP response object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the existing session without creating a new one
        User user = AuthUtils.getAuthenticatedUser(request, response);
        if (user == null) {
            return;   // Already redirected or error sent
        }

        // Fetch a fresh copy of the user from the database to ensure recommendations are loaded
        GenericDao<User> genericDaoUser = new GenericDao<>(User.class);
        User refreshedUser = genericDaoUser.getById(user.getId());

        // Retrieve the user's recommendations
        List<Recommendation> recommendations = refreshedUser.getRecommendations();

        // Set attributes for the JSP view
        request.setAttribute("recommendations", recommendations);

        // Forward the request to the recommendations JSP for rendering
        RequestDispatcher dispatcher = request.getRequestDispatcher("/recommendations.jsp");
        dispatcher.forward(request, response);
    }
}