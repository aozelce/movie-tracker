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

/**
 * Service for interacting with the TMDB (The Movie Database) API to fetch and
 * map movie genre information.
 *
 * @author aozelce
 */
public class TmdbGenreService {

    private static final Logger logger = LogManager.getLogger(TmdbGenreService.class);
    private final String apiKey;
    private final String baseUrl;
    private Map<Integer, String> genreCache;

    /**
     * Instantiates a new Tmdb genre service.
     *
     * @param properties the properties
     */
    public TmdbGenreService(Properties properties) {
        this.apiKey = properties.getProperty("tmdb.api.key");
        this.baseUrl = properties.getProperty("tmdb.base.url");
        this.genreCache = fetchGenres();
    }

    /**
     * Fetches genres from the TMDB API and caches them.
     *
     * @return Map of genre IDs to genre names.
     */
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
     * Retrieves genre names for a list of genre IDs.
     *
     * @param genreIds List of genre IDs.
     * @return Comma-separated genre names.
     */
    public String getGenreNames(List<Integer> genreIds) {
        // If no genre IDs or fetch failed, return empty string
        if (genreIds == null || genreIds.isEmpty()) return "";

        List<String> genreNames = new ArrayList<>();
        for (Integer id : genreIds) {
            // Map each genre ID to its name, skip nulls
            // Fetch genre name from cache
            String genreName = genreCache.get(id);
            if (genreName != null) {
                genreNames.add(genreName);
            }
        }
        // Join genre names with commas
        return String.join(",", genreNames);
    }
}