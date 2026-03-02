package com.aozelce.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientId = (String) req.getServletContext().getAttribute("client.id");
        String loginUrl = (String) req.getServletContext().getAttribute("loginURL");
        String redirectUrl = (String) req.getServletContext().getAttribute("redirectURL");

        if (clientId == null || loginUrl == null || redirectUrl == null) {
            logger.error("Cognito properties were not loaded properly during application startup");
            resp.sendRedirect("error.jsp");
            return;
        }

        String url = loginUrl + "?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUrl;
        resp.sendRedirect(url);
    }
}
