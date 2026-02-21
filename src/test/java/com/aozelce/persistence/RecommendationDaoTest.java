package com.aozelce.persistence;

import com.aozelce.entity.Media;
import com.aozelce.entity.Recommendation;
import com.aozelce.entity.Source;
import com.aozelce.entity.User;
import com.aozelce.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Recommendation dao test.
 */
class RecommendationDaoTest {

    /**
     * The Recommendation dao.
     */
    RecommendationDao recommendationDao;

    /**
     * The Logger.
     */
    Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        logger.info("Running setUp method - resetting database");
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        recommendationDao = new RecommendationDao();
        logger.info("setUp method completed");

    }

    /**
     * Gets recommendation by id success.
     */
    @Test
    void getRecommendationByIdSuccess() {

        Recommendation retrievedRecommendation = recommendationDao.getRecommendationById(1);
        assertNotNull(retrievedRecommendation);
        assertEquals("john_doe", retrievedRecommendation.getUser().getUsername());
        assertEquals("Sarah", retrievedRecommendation.getSource().getName());
        assertEquals("The Bear", retrievedRecommendation.getMedia().getTitle());
        assertEquals("Sarah said best show of 2023!", retrievedRecommendation.getNotes());
        assertFalse(retrievedRecommendation.isWatched());
    }

    /**
     * Update success.
     */
    @Test
    void updateSuccess() {
        // Fetch the existing recommendation
        Recommendation toUpdate = recommendationDao.getRecommendationById(1);
        // Update the notes
        toUpdate.setNotes("Updated notes");
        // Save the updated recommendation
        recommendationDao.saveRecommendation(toUpdate);
        // Fetch it again to verify the update
        Recommendation actual = recommendationDao.getRecommendationById(1);
        // Verify the notes were updated
        assertEquals(toUpdate, actual);
    }

    /**
     * Insert success.
     */
    @Test
    void insertSuccess() {

        // Fetch existing user, source, and media to set up the new recommendation
        UserDao userDao = new UserDao();
        SourceDao sourceDao = new SourceDao();
        MediaDao mediaDao = new MediaDao();
        // Using existing user, source, and media for the new recommendation
        User user = userDao.getUserById(2);
        Source source = sourceDao.getSourceById(1);
        Media media = mediaDao.getMediaById(1);
        // Create a new recommendation
        Recommendation newRecommendation = new Recommendation();
        newRecommendation.setSource(source);
        newRecommendation.setMedia(media);
        newRecommendation.setNotes("New recommendation");
        newRecommendation.setWatched(false);
        // Add the new recommendation to the user's list of recommendations
        user.addRecommendation(newRecommendation);
        // Insert the new recommendation and get the generated ID
        int newRecommendationId = recommendationDao.insert(newRecommendation);
        // Verify the new recommendation was inserted correctly
        Recommendation retrievedRecommendation = recommendationDao.getRecommendationById(newRecommendationId);
        assertNotNull(retrievedRecommendation);
        assertEquals(newRecommendation, retrievedRecommendation);
    }


    /**
     * Delete.
     */
    @Test
    void delete() {
        // Fetch the recommendation to be deleted
        Recommendation recommendationToDelete = recommendationDao.getRecommendationById(1);
        // Delete the recommendation
        recommendationDao.delete(recommendationToDelete);
        assertNull(recommendationDao.getRecommendationById(recommendationToDelete.getId()));

        // Fetch the user and source associated with the deleted recommendation
        UserDao userDao = new UserDao();
        SourceDao sourceDao = new SourceDao();
        User user = recommendationToDelete.getUser();
        Source source = recommendationToDelete.getSource();
        // Verify that the user and source still exist in the database
        assertNotNull(userDao.getUserById(user.getId()));
        assertNotNull(sourceDao.getSourceById(source.getId()));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<Recommendation> recommendations = recommendationDao.getAll();
        assertEquals(12, recommendations.size());
    }

    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        // Retrieve recommendations with notes exactly matching the search term
        List<Recommendation> recommendations = recommendationDao.getByPropertyEqual("notes", "NYT podcast recommended");
        // Assign the retrieved recommendation to a variable for comparison
        Recommendation retrievedRecommendation = recommendations.get(0);
        // Retrieve the recommendation with ID 2 to compare against
        Recommendation expectedRecommendation = recommendationDao.getRecommendationById(2);
        // Verify that the retrieved recommendation matches the expected recommendation
        assertEquals(expectedRecommendation, retrievedRecommendation);
    }

    /**
     * Gets by property like.
     */
    @Test
    void getByPropertyLike() {
        List<Recommendation> recommendations = recommendationDao.getByPropertyLike("notes", "best");
        assertEquals(2, recommendations.size());
    }
}