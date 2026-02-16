package com.aozelce.persistence;

import com.aozelce.entity.User;
import com.aozelce.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        userDao = new UserDao();
        userDao.delete(userDao.getUserById(2));
        assertNull(userDao.getUserById(2));
    }

    @Test
    void getAll() {
        userDao = new UserDao();
        List<User> users = userDao.getAll();
        assertEquals(6, users.size());
    }

    @Test
    void getByPropertyEqual() {
        userDao = new UserDao();
        List<User> users = userDao.getByPropertyEqual("username", "mike");
        assertEquals(1, users.size());
        assertEquals(5, users.get(0).getId());
    }

    @Test
    void getByPropertyLike() {
        userDao = new UserDao();
        List<User> users = userDao.getByPropertyLike("username", "m");
        assertEquals(3, users.size());
    }
}