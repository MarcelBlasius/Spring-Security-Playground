package de.playground.controller;

import de.playground.dtos.User;
import de.playground.security.IAccessUtils;
import de.playground.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTests {
    private IUserService userService;
    private IAccessUtils accessService;
    private UserController controller;

    @BeforeEach
    void setUp() {
        this.userService = Mockito.mock(IUserService.class);
        this.accessService = Mockito.mock(IAccessUtils.class);
        this.controller = new UserController(this.userService, this.accessService);
    }

    @Test
    @DisplayName("Update user works")
    void updateUserTest() {
        // arrange
        var user = new User("username", "123");
        Mockito.when(accessService.authorizeUser(Mockito.any())).thenReturn(true);

        // act
        var response = this.controller.updateUser(user);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("update user returns 400 if user is null")
    void updateNullUserTest() {
        // arrange
        Mockito.when(accessService.authorizeUser("hugo")).thenReturn(true);

        // act
        var response = this.controller.updateUser(null);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User is invalid.", response.getBody());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @EmptySource
    @DisplayName("update user returns 400 if username is null or empty")
    void updateUserWithInvalidUsernameTest(String username) {
        // arrange
        var user = new User(username, "123");

        // act
        var response = this.controller.updateUser(user);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User is invalid.", response.getBody());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @EmptySource
    @DisplayName("update user returns 400 if password is null or empty")
    void updateUserWithInvalidPasswordTest(String password) {
        // arrange
        var user = new User("hugo", password);

        // act
        var response = this.controller.updateUser(user);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User is invalid.", response.getBody());
    }

    @Test
    @DisplayName("delete user works")
    void deleteUserTest() {
        // arrange
        Mockito.when(accessService.authorizeUser("hugo")).thenReturn(true);

        // act
        var response = this.controller.deleteUser("hugo");

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @EmptySource
    @DisplayName("delete user with invalid username returns 400")
    void deleteUserWithInvalidUsername(String username) {
        // act
        var response = this.controller.deleteUser(username);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username is invalid.", response.getBody());
    }

    @Test
    @DisplayName("read user works")
    void getUserTest() {
        // arrange
        var user = new User("hugo", "123");
        Mockito.when(this.userService.readUser(user.getUsername())).thenReturn(user);
        Mockito.when(accessService.authorizeUser("hugo")).thenReturn(true);

        // act
        var response = this.controller.deleteUser(user.getUsername());

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    @EmptySource
    @DisplayName("read user with invalid username returns 400")
    void getUserWithInvalidUsername(String username) {
        // act
        var response = this.controller.deleteUser(username);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Username is invalid.", response.getBody());
    }

    @Test
    @DisplayName("read user with unknown username returns 404")
    void readUserNotFoundReturns404() {
        // arrange
        Mockito.when(this.userService.readUser(Mockito.any())).thenReturn(null);
        Mockito.when(accessService.authorizeUser(Mockito.any())).thenReturn(true);

        // act
        var response = this.controller.readUser("unknown");

        // assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
