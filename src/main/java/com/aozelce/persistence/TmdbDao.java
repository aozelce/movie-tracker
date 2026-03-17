package com.aozelce.persistence;

import com.aozelce.util.PropertiesLoader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.themoviedb.Movie;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.Properties;


public class TmdbDao implements PropertiesLoader {


    Movie getMovie () {

        // Load TMDB settings from tmdb.properties on the classpath
        Properties props;
        try {
            props = loadProperties("/tmdb.properties");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
        ObjectMapper mapper = new ObjectMapper();
        Movie movie= null;
        {
            try {
                movie = mapper.readValue(response, Movie.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return movie;
    }
}