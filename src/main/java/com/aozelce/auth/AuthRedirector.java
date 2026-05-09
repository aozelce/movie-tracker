package com.aozelce.auth;

import com.aozelce.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Utility class for handling authentication-based redirects.
 *
 * @author aozelce
 */
public class AuthRedirector {

    /**
     * Redirects unauthenticated users to the login page or error page.
     *
     * @param request  the request
     * @param response the response
     * @return true if the user is authenticated, false if not
     * @throws IOException the io exception
     */
    public static boolean redirectIfUnauthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Validate user is authenticated
        User user = UserSessionHelper.getUserFromSession(request);

        // Redirect unauthenticated users
        if (user == null) {
            response.sendRedirect("index.jsp");
            return false; // Return false to indicate the user is not authenticated
        }
        return true; // Return true to indicate the user is authenticated
    }
}