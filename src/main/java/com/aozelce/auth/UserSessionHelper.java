package com.aozelce.auth;

import com.aozelce.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Utility service for recommendation-related operations. Currently, provides
 * helper methods for session-based user retrieval.
 *
 * @author aozelce
 */
public class UserSessionHelper {

    /**
     * Helper to retrieve the User object from the session, or null if not
     * present.
     *
     * @param request the request
     * @return the user from session
     */
    public static User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (User) session.getAttribute("user") : null;
    }
}
