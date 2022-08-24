package de.playground.database;

import de.playground.dtos.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserInMemoryRepositoryTests {
    private UserInMemoryRepository repo;
    private Map<String, User> users;

    @BeforeEach
    void setUp() {
        this.users = new HashMap<>();
        this.repo = new UserInMemoryRepository(users);
    }

    @Test
    @DisplayName("Create user works")
    void createUserTest() {
        // arrange
        var user = new User("hugo", "123");

        // act
        this.repo.createUser(user);

        // assert
        assertEquals(1, this.users.size());
        assertEquals(user, this.users.get(user.getUsername()));
    }

    @Test
    @DisplayName("create already existing user does nothing")
    void createAlreadyExistingUserDoesNothingTest() {
        // arrange
        var user = new User("hugo", "123");
        var existingUser = this.insertTestUserToMap();
        this.users.put(existingUser.getUsername(), existingUser);

        // act
        this.repo.createUser(user);

        // assert
        assertEquals(1, this.users.size());
        var actualUser = this.users.get(user.getUsername());
        assertEquals(existingUser.getUsername(), actualUser.getUsername());
        assertEquals(existingUser.getPassword(), actualUser.getPassword());
    }

    @Test
    @DisplayName("read existing user works")
    void readUserTest() {
        // arrange
        var existingUser = this.insertTestUserToMap();

        // act
        var actual = this.repo.readUser(existingUser.getUsername());

        // assert
        assertEquals(existingUser, actual);
    }

    @Test
    @DisplayName("read non existing user returns null")
    void readUserNonExistingReturnsNullTest() {
        // act
        var actual = this.repo.readUser("unknown");

        // assert
        assertNull(actual);
    }

    @Test
    @DisplayName("update user works")
    void updateUserTest() {
        // arrange
        var existingUser = this.insertTestUserToMap();
        var updatedUser = new User(existingUser.getUsername(), "999");

        // act
        this.repo.updateUser(updatedUser);

        // assert
        assertTrue(this.users.containsKey(updatedUser.getUsername()));
        var actual = this.users.get(updatedUser.getUsername());
        assertEquals(updatedUser.getPassword(), actual.getPassword());
    }

    @Test
    @DisplayName("update non existing user does nothing test")
    void updateNonExistingUserDoesNothingTest() {
        // arrange
        var updatedUser = new User("hugo", "999");

        // act
        this.repo.updateUser(updatedUser);

        // assert
        assertEquals(0, this.users.size());
    }

    @Test
    @DisplayName("delete user works")
    void deleteUserTest() {
        // arrange
        var user = this.insertTestUserToMap();

        // act
        this.repo.deleteUser(user.getUsername());

        // assert
        assertEquals(0, this.users.size());
    }

    @Test
    @DisplayName("delete non existing user does nothing")
    void deleteNonExistingUserTest() {
        // arrange
        this.insertTestUserToMap();

        // act
        this.repo.deleteUser("unknown");

        // assert
        assertEquals(1, this.users.size());
    }

    private User insertTestUserToMap() {
        var user = new User("hugo", "321");
        this.users.put(user.getUsername(), user);
        return user;
    }
}
