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
    GenericDao<Source> genericDao;

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
        genericDao = new GenericDao<>(Source.class);
        logger.info("setUp method completed");
    }

    /**
     * Gets source by id success.
     */
    @Test
    void getSourceByIdSuccess() {
        Source retrievedSource = genericDao.getById(1);
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
        Source sourceToUpdate = genericDao.getById(1);
        sourceToUpdate.setName("Sarah Updated");
        genericDao.saveOrUpdate(sourceToUpdate);

        Source actualSource = genericDao.getById(1);
        assertEquals("Sarah Updated", actualSource.getName());
    }

    /**
     * Insert success.
     */
    @Test
    void insertSuccess() {
        GenericDao<User> genericDaoUser = new GenericDao<>(User.class);
        User user = genericDaoUser.getById(2);

        Source newSource = new Source(0, user, "New Source");
        int newSourceId = genericDao.insert(newSource);
        Source retrievedSource = genericDao.getById(newSourceId);
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
        genericDao.delete(genericDao.getById(1));
        assertNull(genericDao.getById(1));
    }

    /**
     * Gets all.
     */
    @Test
    void getAll() {
        List<Source> sources = genericDao.getAll();
        assertEquals(10, sources.size());
    }

    /**
     * Gets by property equal.
     */
    @Test
    void getByPropertyEqual() {
        List<Source> sources = genericDao.getByPropertyEqual("name", "Sarah");
        assertEquals(1, sources.size());
        assertEquals(1, sources.get(0).getId());
        assertEquals(2, sources.get(0).getUser().getId());
    }

    /**
     * Gets by property like.
     */
    @Test
    void getByPropertyLike() {
        List<Source> sources = genericDao.getByPropertyLike("name", "o");
        assertEquals(5, sources.size());
    }
}