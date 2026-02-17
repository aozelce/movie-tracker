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
    MediaDao mediaDao = new MediaDao();

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
        logger.info("setUp method completed");
    }

    /**
     * Gets media by id success.
     */
    @Test
    void getMediaByIdSuccess() {
        mediaDao = new MediaDao();
        Media retrievedMedia = mediaDao.getMediaById(1);
        assertNotNull(retrievedMedia);
        assertEquals("The Bear", retrievedMedia.getTitle());
    }

    /**
     * Update success.
     */
    @Test
    void updateSuccess() {
        mediaDao = new MediaDao();
        Media mediaToUpdate = mediaDao.getMediaById(1);
        mediaToUpdate.setTitle("The Bear Updated");
        mediaDao.saveMedia(mediaToUpdate);

        Media actualMedia = mediaDao.getMediaById(1);
        assertEquals("The Bear Updated", actualMedia.getTitle());
    }

    /**
     * Insert success.
     */
    @Test
    void insertSuccess() {
        mediaDao = new MediaDao();
        Media newMedia = new Media(0, 99999, "Test Movie", "movie", 2024, "/test.jpg", "A test overview.", "Action");
        int newMediaId = mediaDao.insert(newMedia);
        Media retrievedMedia = mediaDao.getMediaById(newMediaId);
        assertNotNull(retrievedMedia);
        assertEquals("Test Movie", retrievedMedia.getTitle());
        assertNotEquals(0, newMediaId);
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        mediaDao = new MediaDao();
        mediaDao.delete(mediaDao.getMediaById(1));
        assertNull(mediaDao.getMediaById(1));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        mediaDao = new MediaDao();
        List<Media> mediaList = mediaDao.getAll();
        assertEquals(12, mediaList.size());
    }

    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        mediaDao = new MediaDao();
        List<Media> mediaList = mediaDao.getByPropertyEqual("title", "Friends");
        assertEquals(1, mediaList.size());
        assertEquals(4, mediaList.get(0).getId());
        assertEquals(1668, mediaList.get(0).getTmdbId());
        assertEquals("tv", mediaList.get(0).getMediaType());
        assertEquals(1994, mediaList.get(0).getYear());
        assertEquals("Comedy", mediaList.get(0).getGenres());
    }

    /**
     * Gets by property like.
     */
    @Test
    void getByPropertyLike() {
        mediaDao = new MediaDao();
        List<Media> mediaList = mediaDao.getByPropertyLike("title", "The");
        assertEquals(4, mediaList.size());
    }
}