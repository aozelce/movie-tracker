package com.aozelce.util;

import com.aozelce.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Utility class for handling authentication-related operations.
 */
public class AuthUtils {

    private static final Logger logger = LogManager.getLogger(AuthUtils.class);

    /**
     * Retrieves the authenticated user from the session. If the user is not authenticated,
     * redirects to the login page or sends an unauthorized error response.
     *
     * @param request  the HTTP request object
     * @param response the HTTP response object
     * @return the authenticated user, or null if not authenticated
     * @throws IOException if an I/O error occurs during redirection or error response
     */
    public static User getAuthenticatedUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = null;

        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (user == null) {
            String loginUrl = (String) request.getServletContext().getAttribute("loginURL");
            if (loginUrl != null) {
                logger.warn("Unauthenticated access attempt. Redirecting to login page: {}", loginUrl);
                response.sendRedirect(loginUrl);
            } else {
                logger.error("Unauthenticated access attempt. No login URL configured.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authenticated");
            }
            return null;
        }

        logger.info("Authenticated user: {}", user.getId());
        return user;
    }
}
