package com.aozelce.persistence;

import com.aozelce.entity.Source;
import com.aozelce.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SourceDaoTest {

    SourceDao sourceDao = new SourceDao();

    Logger logger = LogManager.getLogger(this.getClass());

    @BeforeEach
    void setUp() {
        logger.info("Running setUp method - resetting database");
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        logger.info("setUp method completed");
    }

    @Test
    void getSourceByIdSuccess() {
        sourceDao = new SourceDao();
        Source retrievedSource = sourceDao.getSourceById(1);
        assertNotNull(retrievedSource);
        assertEquals("Sarah", retrievedSource.getName());
        assertEquals(2, retrievedSource.getUserId());
    }

    @Test
    void updateSuccess() {
        sourceDao = new SourceDao();
        Source sourceToUpdate = sourceDao.getSourceById(1);
        sourceToUpdate.setName("Sarah Updated");
        sourceDao.saveSource(sourceToUpdate);

        Source actualSource = sourceDao.getSourceById(1);
        assertEquals("Sarah Updated", actualSource.getName());
    }

    @Test
    void insertSuccess() {
        sourceDao = new SourceDao();
        Source newSource = new Source(0, 2, "New Source");
        int newSourceId = sourceDao.insert(newSource);
        Source retrievedSource = sourceDao.getSourceById(newSourceId);
        assertNotNull(retrievedSource);
        assertEquals("New Source", retrievedSource.getName());
        assertNotEquals(0, newSourceId);
    }

    @Test
    void delete() {
        sourceDao = new SourceDao();
        sourceDao.delete(sourceDao.getSourceById(1));
        assertNull(sourceDao.getSourceById(1));
    }

    @Test
    void getAll() {
        sourceDao = new SourceDao();
        List<Source> sources = sourceDao.getAll();
        assertEquals(10, sources.size());
    }

    @Test
    void getByPropertyEqual() {
        sourceDao = new SourceDao();
        List<Source> sources = sourceDao.getByPropertyEqual("name", "Sarah");
        assertEquals(1, sources.size());
        assertEquals(1, sources.get(0).getId());
        assertEquals(2, sources.get(0).getUserId());
    }

    @Test
    void getByPropertyLike() {
        sourceDao = new SourceDao();
        List<Source> sources = sourceDao.getByPropertyLike("name", "o");
        assertEquals(5, sources.size());
    }
}