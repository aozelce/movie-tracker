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

    RecommendationDao recommendationDao;

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
        recommendationDao = new RecommendationDao();

    }

    /**
     * Gets recommendation by id success.
     */
    @Test
    void getRecommendationByIdSuccess() {
        recommendationDao = new RecommendationDao();
        Recommendation retrieved = recommendationDao.getRecommendationById(1);
        assertNotNull(retrieved);
        assertEquals("john_doe", retrieved.getUser().getUsername());
        assertEquals("Sarah", retrieved.getSource().getName());
        assertEquals("The Bear", retrieved.getMedia().getTitle());
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

        // fetch the real objects first
        UserDao userDao = new UserDao();
        SourceDao sourceDao = new SourceDao();
        MediaDao mediaDao = new MediaDao();

        User user = userDao.getUserById(2);
        Source source = sourceDao.getSourceById(1);
        Media media = mediaDao.getMediaById(1);

        Recommendation newRec = new Recommendation();
        newRec.setUser(user);
        newRec.setSource(source);
        newRec.setMedia(media);
        newRec.setNotes("New recommendation");
        newRec.setWatched(false);

        int newId = recommendationDao.insert(newRec);

        Recommendation retrieved = recommendationDao.getRecommendationById(newId);
        assertNotNull(retrieved);
        assertNotEquals(0, newId);
        assertEquals("New recommendation", retrieved.getNotes());
        assertEquals("john_doe", retrieved.getUser().getUsername());
        assertEquals("Sarah", retrieved.getSource().getName());
        assertEquals("The Bear", retrieved.getMedia().getTitle());
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