package com.aozelce.controller;

import org.apache.logging.log4j.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(
        urlPatterns = {"/logIn"}
)

/** Begins the authentication process using AWS Cognito
 *
 */
public class LogIn extends HttpServlet {
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Route to the aws-hosted cognito login page.
     * @param req servlet request
     * @param resp servlet response
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String clientId = (String) req.getServletContext().getAttribute("client.id");
        String loginUrl = (String) req.getServletContext().getAttribute("loginURL");
        String redirectUrl = (String) req.getServletContext().getAttribute("redirectURL");

        if (clientId == null || loginUrl == null || redirectUrl == null) {
            logger.error("Cognito properties were not loaded properly during application startup");
            resp.sendRedirect("error.jsp");
            return;
        }
        // Added profile to the scope so i can retrieve the preferred username attribute
        // This is used to display the user's name in the navbar after logging in
        String url = loginUrl + "?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUrl + "&scope=openid+email+profile";
        resp.sendRedirect(url);
    }
}
