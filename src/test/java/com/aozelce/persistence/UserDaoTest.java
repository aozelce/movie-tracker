package com.aozelce.persistence;

import com.aozelce.entity.User;
import com.aozelce.util.Database;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type User dao test.
 */
class UserDaoTest {

    /**
     * The User dao.
     */
    UserDao userDao = new UserDao();

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
     * Tests the successful retrieval of a user by their ID using the UserDao class.
     * - Creates an instance of the UserDao class.
     * - Calls the getUserById method with a valid user ID.
     * - Asserts that the retrieved user object is not null.
     * - Asserts that the username of the retrieved user matches the expected value.
     */
    @Test
    void getUserByIdSuccess() {
        userDao = new UserDao();
        User retrievedUser = userDao.getUserById(2);
        assertNotNull(retrievedUser);
        assertEquals("john_doe", retrievedUser.getUsername());

    }

    /**
     * Tests the successful update of a user's data in the database.
     * - Retrieves a user by ID using the UserDao class.
     * - Updates the user's username.
     * - Saves the updated user back to the database.
     * - Retrieves the user again by ID to confirm the username was updated.
     * - Asserts that the updated username matches the expected value
     */
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

    /**
     * Tests the successful insertion of a new user into the database.
     * - Creates a new instance of the UserDao class.
     * - Instantiates a new User object with specific details (email and username).
     * - Calls the `insert` method of the UserDao class to persist the User object.
     * - Retrieves the User object from the database using its generated ID.
     * - Asserts that the retrieved User object is not null.
     * - Asserts that the username of the retrieved User matches the expected username.
     * - Asserts that the generated ID of the User is not the default value (0).
     */
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

    /**
     * Tests the deletion of a user from the database.
     */
    @Test
    void delete() {
        userDao = new UserDao();
        userDao.delete(userDao.getUserById(2));
        assertNull(userDao.getUserById(2));
    }

    /**
     * Tests the retrieval of all users from the database.
     * - Instantiates the UserDao class.
     * - Calls the `getAll` method to retrieve the list of all users.
     * - Asserts that the size of the retrieved user list matches the expected value.

     */
    @Test
    void getAll() {
        userDao = new UserDao();
        List<User> users = userDao.getAll();
        assertEquals(6, users.size());
    }

    /**
     * Tests the retrieval of users by a specified property and value using the `getByPropertyEqual` method of the UserDao class.
     * - Instantiates the UserDao class.
     * - Calls the `getByPropertyEqual` method with a property name and a corresponding value.
     * - Asserts that the size of the retrieved user list matches the expected value.
     * - Asserts that the retrieved user has the expected properties, such as ID.
     */
    @Test
    void getByPropertyEqual() {
        userDao = new UserDao();
        List<User> users = userDao.getByPropertyEqual("username", "mike");
        assertEquals(1, users.size());
        assertEquals(5, users.get(0).getId());
    }

    /**
     * Tests the retrieval of users by a property with a partial matching value using the `getByPropertyLike` method of the `UserDao` class.
     * It performs the following steps:
     * - Instantiates the `UserDao` class.
     * - Calls the `getByPropertyLike` method with a specific property name (`"username"`) and a partial value (`"m"`).
     * - Asserts that the size of the returned user list matches the expected size.
     */
    @Test
    void getByPropertyLike() {
        userDao = new UserDao();
        List<User> users = userDao.getByPropertyLike("username", "m");
        assertEquals(3, users.size());
    }
}