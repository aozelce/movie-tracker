package com.aozelce.persistence;

// Load shared properties helper
import com.aozelce.util.PropertiesLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.themoviedb.Movie;
import org.junit.Test;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

public class TestServiceClient implements PropertiesLoader {

    @Test
    public void testTmdbJSON() throws Exception {
        // Load TMDB settings from tmdb.properties on the classpath
        Properties props = loadProperties("/tmdb.properties");
        // Pull the base URL
        String baseUrl = props.getProperty("tmdb.base.url");
        // Pull the API key
        String apiKey = props.getProperty("tmdb.api.key");
        // Create a Jersey client
        Client client = ClientBuilder.newClient();
        // Build the search/movie endpoint with query and API key
        WebTarget target = client.target(baseUrl)
                .path("search")
                .path("movie")
                .queryParam("api_key", apiKey)
                .queryParam("query", "The Matrix");
        // Execute the GET as JSON
        String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
        //
        ObjectMapper mapper = new ObjectMapper();
        Movie movie = mapper.readValue(response, Movie.class);
        String expectedMovieTitle = "The Matrix";
        String actualMovieTitle = movie.getResults().get(0).getTitle();
        assertEquals(expectedMovieTitle, actualMovieTitle);
    }
}