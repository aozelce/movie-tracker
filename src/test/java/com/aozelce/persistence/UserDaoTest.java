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
 * The type User dao test.
 */
class UserDaoTest {

    /**
     * The User dao.
     */
    GenericDao<User> genericDao;

    /**
     * The Logger.
     */
    Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleanDB.sql");
        genericDao = new GenericDao<>(User.class);
    }

    /**
     * Tests the successful retrieval of a user by their ID using the UserDao
     * class. - Creates an instance of the UserDao class. - Calls the getById
     * method with a valid user ID. - Asserts that the retrieved user object is
     * not null. - Asserts that the username of the retrieved user matches the
     * expected value.
     */
    @Test
    void getUserByIdSuccess() {
        // Retrieve a user by ID
        User retrievedUser = genericDao.getById(2);
        // Assert that the retrieved user is not null and has the expected username
        assertNotNull(retrievedUser);
        // Assert that the username of the retrieved user matches the expected value
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
        // Retrieve the user to update
        User userToUpdate = genericDao.getById(1);
        // Update the user's username
        userToUpdate.setUsername("Smith");
        // Save the updated user back to the database
        genericDao.saveOrUpdate(userToUpdate);
        // Retrieve the user and check that the name change worked
        User actualUser = genericDao.getById(1);
        // Assert that the updated user matches the expected user
        assertEquals(userToUpdate, actualUser);
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
        // Create a new user to insert
        User newUser = new User("dan@email.com", "dan");
        // Insert the new user and get the generated ID
        int newUserId = genericDao.insert(newUser);
        // Retrieve the user by the generated ID
        User retrievedUser = genericDao.getById(newUserId);
        // Assert that the retrieved user is not null
        assertNotNull(retrievedUser);
        // Assert that the user matches with the expected user
        assertEquals(newUser, retrievedUser);
    }

    /**
     * Insert with recommendations success.
     */
    @Test
    void insertWithRecommendationsSuccess() {
        // Create DAOs needed to fetch related objects
        GenericDao<Media> genericDaoMedia = new GenericDao<>(Media.class);
        GenericDao<Source> genericDaoSource = new GenericDao<>(Source.class);

        // Fetch real media and source objects to satisfy not-null constraints
        Media media = genericDaoMedia.getById(1);
        Source source = genericDaoSource.getById(1);

        // Create a new user to insert
        User newUser = new User("charlie@email.com", "charlie");

        // Create the first recommendation and associate it with user, media, and source
        Recommendation rec1 = new Recommendation();
        rec1.setNotes("Smart, layered sci-fi with stunning visuals");
        rec1.setWatched(false);
        rec1.setMedia(media);
        rec1.setSource(source);
        rec1.setUser(newUser);

        // Create a second recommendation and associate it with user, media, and source
        Recommendation rec2 = new Recommendation();
        rec2.setNotes("Stylish, bittersweet romantic musical");
        rec2.setWatched(true);
        rec2.setMedia(media);
        rec2.setSource(source);
        rec2.setUser(newUser);

        // Add both recommendations to the user's list
        newUser.addRecommendation(rec1);
        newUser.addRecommendation(rec2);

        // Insert the new user and get the generated ID
        int newUserId = genericDao.insert(newUser);

        // Retrieve the user by the generated ID
        User retrievedUser = genericDao.getById(newUserId);

        // Assert that the retrieved user is not null
        assertNotNull(retrievedUser);

        // Assert that the retrieved user matches the inserted user
        assertEquals(newUser, retrievedUser);

        // Assert that both recommendations were saved and associated with the user
        assertEquals(2, retrievedUser.getRecommendations().size());
        assertEquals("Smart, layered sci-fi with stunning visuals", retrievedUser.getRecommendations().get(0).getNotes());
        assertEquals("Stylish, bittersweet romantic musical", retrievedUser.getRecommendations().get(1).getNotes());
    }

    /**
     * Tests the deletion of a user from the database.
     */
    @Test
    void delete() {
        // Create a new user to delete
        User user = new User();
        user.setId(2);
        // Delete the user
        genericDao.delete(user);
        // Verify the user was deleted
        assertNull(genericDao.getById(user.getId()));
    }

    /**
     * Tests the deletion of recommendations from the database when a user was
     * deleted.
     */
    @Test
    void deleteWithUser() {
        // Get the user to delete
        User userToDelete = genericDao.getById(3);

        // Get the associated orders
        List<Recommendation> recommendations = userToDelete.getRecommendations();
        int recommendation1Id = recommendations.get(0).getId();
        int recommendation2Id = recommendations.get(1).getId();

        // Delete the user
        genericDao.delete(userToDelete);

        //Verify user was deleted
        assertNull(genericDao.getById(3));

        // Verify the recommendations were also deleted
        GenericDao<Recommendation> genericDaoRec=
                new GenericDao<>(Recommendation.class);
        assertNull(genericDaoRec.getById(recommendation1Id));
        assertNull(genericDaoRec.getById(recommendation2Id));
    }

    /**
     * Tests the retrieval of all users from the database.
     * - Instantiates the UserDao class.
     * - Calls the `getAll` method to retrieve the list of all users.
     * - Asserts that the size of the retrieved user list matches the expected value.
     */
    @Test
    void getAll() {

        List<User> users = genericDao.getAll();
        assertEquals(7, users.size());
    }

    /**
     * Tests the retrieval of users by a specified property and value using the `getByPropertyEqual` method of the
     * UserDao class.
     * - Instantiates the UserDao class.
     * - Calls the `getByPropertyEqual` method with a property name and a corresponding value.
     * - Asserts that the size of the retrieved user list matches the expected value.
     * - Asserts that the retrieved user has the expected properties, such as ID.
     */
    @Test
    void getByPropertyEqual() {
        // Assign the result of the getByPropertyEqual method to a list of users
        List<User> users = genericDao.getByPropertyEqual("username", "mike");
        // Assert that the size of the retrieved user list
        assertEquals(1, users.size());
        // Assert that the ID of the retrieved user matches the expected value
        assertEquals(5, users.get(0).getId());
    }

    /**
     * Tests the retrieval of users by a property with a partial matching value using the `getByPropertyLike` method
     * of the `UserDao` class.
     * It performs the following steps:
     * - Instantiates the `UserDao` class.
     * - Calls the `getByPropertyLike` method with a specific property name (`"username"`) and a partial value (`"m"`).
     * - Asserts that the size of the returned user list matches the expected size.
     */
    @Test
    void getByPropertyLike() {

        List<User> users = genericDao.getByPropertyLike("username", "m");
        assertEquals(3, users.size());
    }
}