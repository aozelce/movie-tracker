package com.aozelce.persistence;

import com.aozelce.entity.Media;
import com.aozelce.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Media dao test.
 */
class MediaDaoTest {

    /**
     * The Media dao.
     */
    GenericDao<Media> genericDao;

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
        genericDao = new GenericDao<>(Media.class);
        logger.info("setUp method completed");
    }

    /**
     * Gets media by id success.
     */
    @Test
    void getMediaByIdSuccess() {

        Media retrievedMedia = genericDao.getById(1);
        assertNotNull(retrievedMedia);
        assertEquals("The Bear", retrievedMedia.getTitle());
    }

    /**
     * Update success.
     */
    @Test
    void updateSuccess() {
        Media mediaToUpdate = genericDao.getById(1);
        mediaToUpdate.setTitle("The Bear Updated");
        genericDao.saveOrUpdate(mediaToUpdate);

        Media actualMedia = genericDao.getById(1);
        assertEquals("The Bear Updated", actualMedia.getTitle());
    }

    /**
     * Insert success.
     */
    @Test
    void insertSuccess() {
        Media newMedia = new Media(0, 99999, "Test Movie",
                "movie", 2024, "/test.jpg", "A test overview.", "Action");
        int newMediaId = genericDao.insert(newMedia);
        Media retrievedMedia = genericDao.getById(newMediaId);
        assertNotNull(retrievedMedia);
        assertEquals("Test Movie", retrievedMedia.getTitle());
        assertNotEquals(0, newMediaId);
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        genericDao.delete(genericDao.getById(1));
        assertNull(genericDao.getById(1));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<Media> mediaList = genericDao.getAll();
        assertEquals(12, mediaList.size());
    }

    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        List<Media> mediaList = genericDao.getByPropertyEqual("title", "Friends");
        assertEquals(1, mediaList.size());
        assertEquals(4, mediaList.get(0).getId());
        assertEquals(1668, mediaList.get(0).getTmdbId());
        assertEquals("tv", mediaList.get(0).getMediaType());
        assertEquals(1994, mediaList.get(0).getYear());
        assertEquals("Comedy", mediaList.get(0).getGenres());
    }

    /**
     * Gets by property equal with integer value.
     */
    @Test
    void getByPropertyEqualWithInt() {
        List<Media> mediaList = genericDao.getByPropertyEqual("tmdbId", 1668);
        assertEquals(1, mediaList.size());
        assertEquals("Friends", mediaList.get(0).getTitle());
    }

    /**
     * Gets by property like.
     */
    @Test
    void getByPropertyLike() {
        List<Media> mediaList = genericDao.getByPropertyLike("title", "The");
        assertEquals(4, mediaList.size());
    }
}