package com.aozelce.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet responsible for handling user logout operations.
 *
 * @author aozelce
 */
@WebServlet("/logOut")
public class LogOut extends HttpServlet {

    /** Logger instance for tracking logout operations and errors */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Handles GET requests to log out the user.
     * Simply invalidates the session and redirects to the index page.
     *
     * @param request  the HTTP request object
     * @param response the HTTP response object
     * @throws IOException      if an I/O error occurs during redirection
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Invalidate the session if it exists
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        logger.info("User logged out, redirecting to index page");

        // Redirect to index.jsp
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}

