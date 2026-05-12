package com.aozelce.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for interacting with the TMDB (The Movie Database) API to fetch and
 * map movie genre information.
 *
 * @author aozelce
 */
public class TmdbGenreService {
    // Logger for debugging and error messages
    private static final Logger logger = LogManager.getLogger(TmdbGenreService.class);
    // TMDB API key and base URL, loaded from properties
    private final String apiKey;
    private final String baseUrl;

    /**
     * Instantiates a new Tmdb genre service.
     *
     * @param properties the properties
     */
// Constructor: loads API key and base URL from properties file
    public TmdbGenreService(Properties properties) {
        this.apiKey = properties.getProperty("tmdb.api.key");
        this.baseUrl = properties.getProperty("tmdb.base.url");
    }

    // Fetches the genre list from TMDB API every time it's called
    private Map<Integer, String> fetchGenres() {
        try {
            // Create a new HTTP client
            Client client = ClientBuilder.newClient();
            // Build the API endpoint URL for genre list
            WebTarget target = client.target(baseUrl)
                    .path("genre").path("movie").path("list") // /genre/movie/list
                    .queryParam("api_key", apiKey) // Add API key as query parameter
                    .queryParam("language", "en-US"); // Set language to English
            // Make the GET request and get the response as a JSON string
            String response = target.request(MediaType.APPLICATION_JSON).get(String.class);
            // Parse the JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            // Create a map to hold genre ID to name mapping
            Map<Integer, String> map = new HashMap<>();
            // Loop through each genre in the JSON and add to the map
            for (JsonNode genre : root.get("genres")) {
                map.put(genre.get("id").asInt(), genre.get("name").asText());
            }
            logger.info("Fetched {} TMDB genres", map.size());
            return map;
        } catch (Exception e) {
            // Log any errors and return an empty map
            logger.error("Failed to fetch TMDB genres from API", e);
            return Collections.emptyMap();
        }
    }

    /**
     * Gets genre names.
     *
     * @param genreIds the genre ids
     * @return the genre names
     */
// Converts a list of genre IDs to a comma-separated string of genre names
    public String getGenreNames(List<Integer> genreIds) {
        // Fetch the latest genre map from TMDB API
        Map<Integer, String> genreMap = fetchGenres();
        // If no genre IDs or fetch failed, return empty string
        if (genreIds == null || genreIds.isEmpty()) return "";
        // Map each genre ID to its name, skip nulls, and join with commas
        // Reference: https://www.baeldung.com/java-maps-streams
        return genreIds.stream()
                //For each element in the stream (each genre ID), call the
                // get method on genreMap with that element as the argument.
                .map(genreMap::get)
                .filter(name -> name != null)
                .collect(Collectors.joining(","));
    }
}