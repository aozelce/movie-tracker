package com.aozelce.persistence;

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
 * The type Source dao test.
 */
class SourceDaoTest {

    /**
     * The Source dao.
     */
    SourceDao sourceDao = new SourceDao();

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
     * Gets source by id success.
     */
    @Test
    void getSourceByIdSuccess() {
        sourceDao = new SourceDao();
        Source retrievedSource = sourceDao.getSourceById(1);
        assertNotNull(retrievedSource);
        assertEquals(1, retrievedSource.getId());
        assertEquals("Sarah", retrievedSource.getName());
        assertEquals(2, retrievedSource.getUser().getId());
    }

    /**
     * Update success.
     */
    @Test
    void updateSuccess() {
        sourceDao = new SourceDao();
        Source sourceToUpdate = sourceDao.getSourceById(1);
        sourceToUpdate.setName("Sarah Updated");
        sourceDao.saveSource(sourceToUpdate);

        Source actualSource = sourceDao.getSourceById(1);
        assertEquals("Sarah Updated", actualSource.getName());
    }

    /**
     * Insert success.
     */
    @Test
    void insertSuccess() {
        sourceDao = new SourceDao();
        UserDao userDao = new UserDao();
        User user = userDao.getUserById(2);

        Source newSource = new Source(0, user, "New Source");
        int newSourceId = sourceDao.insert(newSource);
        Source retrievedSource = sourceDao.getSourceById(newSourceId);
        assertNotNull(retrievedSource);
        assertEquals("New Source", retrievedSource.getName());
        assertNotEquals(0, newSourceId);
        assertEquals(2, retrievedSource.getUser().getId());
    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        sourceDao = new SourceDao();
        sourceDao.delete(sourceDao.getSourceById(1));
        assertNull(sourceDao.getSourceById(1));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        sourceDao = new SourceDao();
        List<Source> sources = sourceDao.getAll();
        assertEquals(10, sources.size());
    }

    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        sourceDao = new SourceDao();
        List<Source> sources = sourceDao.getByPropertyEqual("name", "Sarah");
        assertEquals(1, sources.size());
        assertEquals(1, sources.get(0).getId());
        assertEquals(2, sources.get(0).getUser().getId());
    }

    /**
     * Gets by property like.
     */
    @Test
    void getByPropertyLike() {
        sourceDao = new SourceDao();
        List<Source> sources = sourceDao.getByPropertyLike("name", "o");
        assertEquals(5, sources.size());
    }
}