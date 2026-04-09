package com.aozelce.persistence;

import com.themoviedb.Movie;
import com.themoviedb.ResultsItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Properties;


/**
 * Unit tests for the TmdbDao class, focusing on movie search functionality using TheMovieDB API.
 */
class TmdbDaoTest {

    TmdbDao dao;
    Movie movie;

    /**
     * Sets up the test environment by loading TMDB properties and initializing the DAO and a sample movie.
     */
    @BeforeEach
    void setUp() throws Exception{
        // Create a new Properties object
        Properties properties = new Properties();
        // Load TMDB properties from the resource file
        properties.load(getClass().getResourceAsStream("/tmdb.properties"));
        // Initialize the TmdbDao with the loaded properties
        dao = new TmdbDao(properties);
        // Search for the movie "The Crack: Inception" and assign the result to the movie variable
        movie = dao.searchMovie("The Crack: Inception");
    }

    /**
     * Tests the searchMovie method to ensure it returns the correct movie results.
     */
    @Test
    void searchMovie() {
        // Assert that the movie object is not null
        assertNotNull(movie);
        // Get the list of ResultsItem objects from the movie
        List<ResultsItem> results = movie.getResults();
        // Iterate through each ResultsItem in the results list
        for (ResultsItem result : results) {
            // Get the title of the current result
            String title = result.getTitle();
            // Assert that the title matches the expected movie title
            assertEquals("The Crack: Inception", title);
        }
    }
}