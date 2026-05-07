package com.aozelce.persistence;

import com.aozelce.util.PropertiesLoader;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.themoviedb.Movie;
import com.themoviedb.ResultsItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Properties;


/**
 * The type Tmdb dao.
 * Responsible for making API calls to The Movie Database (TMDB).
 */
public class TmdbDao {

    // Logger instance for debugging and error tracking
    private final Logger logger = LogManager.getLogger(this.getClass());

    private Properties properties;

    /**
     * Instantiates a new Tmdb dao.
     */
    public TmdbDao() {};

    /**
     * Instantiates a new Tmdb dao.
     *
     * @param properties the properties
     */
    public TmdbDao(Properties properties) {
        this();
        this.properties = properties;
    };

    /**
     * Searches for a movie from TMDB API.
     * Retrieves TMDB properties from application scope (loaded at startup) instead of reloading from file.
     *
     * @param searchQuery the search query to find movies
     * @return Movie object with results (may contain 0 results if not found), or null if API call fails
     */
    public Movie searchMovie(String searchQuery) {

        // Retrieve TMDB base URL from application scope (loaded once at startup)
        String baseUrl = properties.getProperty("tmdb.base.url");
        
        // Retrieve TMDB API key from application scope (loaded once at startup)
        String apiKey = properties.getProperty("tmdb.api.key");

        // Validate that properties were loaded properly
        if (baseUrl == null || apiKey == null) {
            logger.error("TMDB properties were not loaded properly during application startup");
            return null;
        }

        try {
            // Create a Jersey client
            Client client = ClientBuilder.newClient();

            // Build the search/movie endpoint with query and API key
            WebTarget target = client.target(baseUrl)
                    .path("search")
                    .path("multi")
                    .queryParam("api_key", apiKey)
                    .queryParam("query", searchQuery);

            // Execute the GET request and retrieve response as JSON string
            String response = target.request(MediaType.APPLICATION_JSON).get(String.class);

            // Create an ObjectMapper to convert JSON response to Movie object
            ObjectMapper mapper = new ObjectMapper();
            Movie movie = mapper.readValue(response, Movie.class);

            // Filter out results where mediaType is not 'movie' or 'tv'
            if (movie != null && movie.getResults() != null) {
                List<ResultsItem> filtered = new java.util.ArrayList<>();
                for (ResultsItem item : movie.getResults()) {
                    String type = item.getMediaType();
                    if ("movie".equals(type) || "tv".equals(type)) {
                        filtered.add(item);
                    }
                }
                movie.setResults(filtered);
            }

            return movie;
        } catch (JsonProcessingException e) {
            logger.error("Error parsing TMDB API response for query: {}", searchQuery, e);
            return null;
        } catch (Exception e) {
            logger.error("Error calling TMDB API for query: {}", searchQuery, e);
            return null;
        }
    }
}