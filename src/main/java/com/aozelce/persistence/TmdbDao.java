package com.aozelce.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.themoviedb.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


/**
 * The type Tmdb dao.
 * Responsible for making API calls to The Movie Database (TMDB).
 */
public class TmdbDao {

    // Logger instance for debugging and error tracking
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Stores the ServletContext for accessing application-scoped TMDB properties
    private final ServletContext servletContext;

    /**
     * Constructor that requires ServletContext to access TMDB properties loaded at startup
     *
     * @param servletContext the ServletContext containing TMDB properties
     */
    public TmdbDao(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /**
     * Gets movie from TMDB API.
     * Retrieves TMDB properties from application scope (loaded at startup) instead of reloading from file.
     *
     * @return the movie
     */
    Movie getMovie() {

        // Retrieve TMDB base URL from application scope (loaded once at startup)
        String baseUrl = (String) servletContext.getAttribute("tmdb.base.url");
        
        // Retrieve TMDB API key from application scope (loaded once at startup)
        String apiKey = (String) servletContext.getAttribute("tmdb.api.key");

        // Validate that properties were loaded properly
        if (baseUrl == null || apiKey == null) {
            logger.error("TMDB properties were not loaded properly during application startup");
            throw new RuntimeException("TMDB properties not available in application scope");
        }

        // Create a Jersey client
        Client client = ClientBuilder.newClient();
        
        // Build the search/movie endpoint with query and API key
        WebTarget target = client.target(baseUrl)
                .path("search")
                .path("movie")
                .queryParam("api_key", apiKey)
                .queryParam("query", "The Matrix");

        // Execute the GET request and retrieve response as JSON string
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        
        // Create an ObjectMapper to convert JSON response to Movie object
        ObjectMapper mapper = new ObjectMapper();
        Movie movie = null;
        
        try {
            // Convert JSON response to Movie object
            movie = mapper.readValue(response, Movie.class);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing TMDB API response", e);
            throw new RuntimeException(e);
        }

        return movie;
    }
}