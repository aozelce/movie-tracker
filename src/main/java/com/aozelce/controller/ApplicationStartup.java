package com.aozelce.controller;

import com.aozelce.util.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Properties;

/**
 * Startup servlet that loads AWS Cognito properties once during application initialization.
 * This centralizes property loading and stores them in the application scope for use by other servlets.
 */
@WebServlet(
        name = "applicationStartup",
        urlPatterns = {"/movie-tracker-startup"},
        loadOnStartup = 1
)
public class ApplicationStartup extends HttpServlet implements PropertiesLoader {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext context = getServletContext();
        loadCognitoProperties(context);
    }

    /**
     * Load AWS Cognito properties from cognito.properties file and store them in application scope.
     * This eliminates code duplication across multiple servlets.
     *
     * @param context the ServletContext to store properties in
     */
    private void loadCognitoProperties(ServletContext context) {
        try {
            Properties properties = loadProperties("/cognito.properties");

            // Store all Cognito properties in application scope
            context.setAttribute("client.id", properties.getProperty("client.id"));
            context.setAttribute("client.secret", properties.getProperty("client.secret"));
            context.setAttribute("loginURL", properties.getProperty("loginURL"));
            context.setAttribute("redirectURL", properties.getProperty("redirectURL"));
            context.setAttribute("oauthURL", properties.getProperty("oauthURL"));
            context.setAttribute("region", properties.getProperty("region"));
            context.setAttribute("poolId", properties.getProperty("poolId"));

            logger.info("Cognito properties loaded successfully and stored in application scope");
        } catch (IOException ioException) {
            logger.error("Cannot load Cognito properties: " + ioException.getMessage(), ioException);
        } catch (Exception e) {
            logger.error("Error loading Cognito properties: " + e.getMessage(), e);
        }
    }
}

