package com.aozelce.persistence;

import com.aozelce.entity.Recommendation;
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

    RecommendationDao recommendationDao = new RecommendationDao();

    Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        logger.info("Running setUp method - resetting database");
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        logger.info("setUp method completed");
    }

    /**
     * Gets recommendation by id success.
     */
    @Test
    void getRecommendationByIdSuccess() {
        recommendationDao = new RecommendationDao();
        Recommendation retrieved = recommendationDao.getRecommendationById(1);
        assertNotNull(retrieved);
        assertEquals(2, retrieved.getUserId());
        assertEquals(1, retrieved.getSourceId());
        assertEquals(1, retrieved.getMediaId());
        assertEquals("Sarah said best show of 2023!", retrieved.getNotes());
        assertFalse(retrieved.isWatched());
    }

    /**
     * Update success.
     */
    @Test
    void updateSuccess() {
        recommendationDao = new RecommendationDao();
        Recommendation toUpdate = recommendationDao.getRecommendationById(1);
        toUpdate.setNotes("Updated notes");
        recommendationDao.saveRecommendation(toUpdate);

        Recommendation actual = recommendationDao.getRecommendationById(1);
        assertEquals("Updated notes", actual.getNotes());
    }

    /**
     * Insert success.
     */
    @Test
    void insertSuccess() {
        recommendationDao = new RecommendationDao();
        Recommendation newRec = new Recommendation(0, 2, 1, 1, "New recommendation", false);
        int newId = recommendationDao.insert(newRec);
        Recommendation retrieved = recommendationDao.getRecommendationById(newId);
        assertNotNull(retrieved);
        assertEquals("New recommendation", retrieved.getNotes());
        assertNotEquals(0, newId);
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        recommendationDao = new RecommendationDao();
        recommendationDao.delete(recommendationDao.getRecommendationById(1));
        assertNull(recommendationDao.getRecommendationById(1));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        recommendationDao = new RecommendationDao();
        List<Recommendation> recommendations = recommendationDao.getAll();
        assertEquals(12, recommendations.size());
    }

    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        recommendationDao = new RecommendationDao();
        List<Recommendation> recommendations = recommendationDao.getByPropertyEqual("notes", "NYT podcast recommended");
        assertEquals(1, recommendations.size());
        assertEquals(2, recommendations.get(0).getId());
    }

    @Test
    void getByPropertyLike() {
        recommendationDao = new RecommendationDao();
        List<Recommendation> recommendations = recommendationDao.getByPropertyLike("notes", "best");
        assertEquals(2, recommendations.size());
    }
}