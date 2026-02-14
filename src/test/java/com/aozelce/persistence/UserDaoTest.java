package com.aozelce.persistence;

import com.aozelce.entity.User;
import com.aozelce.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    UserDao userDao = new UserDao();

    Logger logger = LogManager.getLogger(this.getClass());

    @BeforeEach
    void setUp() {
        logger.info("Running setUp method - resetting database");
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        logger.info("setUp method completed");
    }

    @Test
    void getUserByIdSuccess() {
        userDao = new UserDao();
        User retrievedUser = userDao.getUserById(2);
        assertNotNull(retrievedUser);
        assertEquals("john_doe", retrievedUser.getUsername());

    }

    @Test
    void updateSuccess() {
        userDao = new UserDao();
        User userToUpdate = userDao.getUserById(1);
        userToUpdate.setUsername("Smith");
        userDao.saveUser(userToUpdate);

        // retrieve the user and check that the name change worked
        User actualUser = userDao.getUserById(1);
        assertEquals("Smith", actualUser.getUsername());



    }

    @Test
    void insertSuccess() {
        UserDao userDao = new UserDao();
        User newUser = new User("ken@email.com", "ken");
        int newUserId = userDao.insert(newUser);
        User retrievedUser = userDao.getUserById(newUserId);
        assertNotNull(retrievedUser);
        assertEquals("ken", retrievedUser.getUsername());
        assertNotEquals(0, newUserId);

    }

    @Test
    void delete() {

    }

    @Test
    void getAll() {
    }

    @Test
    void getByPropertyEqual() {
    }

    @Test
    void getByPropertyLike() {
    }
}