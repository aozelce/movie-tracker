package com.aozelce.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Tmdb genre service test.
 */
class TmdbGenreServiceTest {

    /**
     * Gets genre names.
     */
    @Test
    void getGenreNames() {
        // Arrange: load TMDB properties
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/tmdb.properties"));
        } catch (Exception e) {
            fail("Failed to load tmdb.properties: " + e.getMessage());
        }
        TmdbGenreService service = new TmdbGenreService(properties);

        // Example genre IDs for popular genres (e.g., 28=Action, 35=Comedy, 18=Drama)
        List<Integer> genreIds = Arrays.asList(28, 35, 18);
        String genreNames = service.getGenreNames(genreIds);

        // Assert: genreNames should contain the expected genre names (order may vary)
        assertNotNull(genreNames);
        assertFalse(genreNames.isEmpty());
        // Should contain at least one of the expected genre names
        assertTrue(genreNames.contains("Action") || genreNames.contains("Comedy") || genreNames.contains("Drama"));
    }
}