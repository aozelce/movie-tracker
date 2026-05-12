package com.aozelce.persistence;

import com.aozelce.service.TmdbMediaService;
import com.themoviedb.Movie;
import com.themoviedb.ResultsItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the TmdbMediaService class, focusing on movie search functionality
 * using TheMovieDB API.
 */
class TmdbMediaServiceTest {

    /**
     * The Dao.
     */
    TmdbMediaService dao;
    /**
     * The Movie.
     */
    Movie movie;

    /**
     * Sets up the test environment by loading TMDB properties and initializing
     * the DAO and a sample movie.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception{
        // Create a new Properties object
        Properties properties = new Properties();
        // Load TMDB properties from the resource file
        properties.load(getClass().getResourceAsStream("/tmdb.properties"));
        // Initialize the TmdbMediaService with the loaded properties
        dao = new TmdbMediaService(properties);
    }

    /**
     * Tests the searchMovie method to ensure it returns the correct movie
     * results.
     */
    @Test
    void searchMovie() {
        // Search for movies with the keyword "Matrix"
        movie = dao.searchMovie("Matrix");

        // Assert that the movie object is not null
        assertNotNull(movie);

        // Get the list of ResultsItem objects from the movie
        List<ResultsItem> results = movie.getResults();
        assertNotNull(results);
        assertFalse(results.isEmpty());

        // Flag to indicate if a result with the exact title "Matrix" is found
        boolean found = false;

        // Iterate through the results to find a match
        for (ResultsItem result : results) {
            if ("Matrix".equals(result.getTitle())) {
                found = true;
                break;
            }
        }

        // Assert that at least one result with the title "Matrix" exists
        assertTrue(found);
    }
}