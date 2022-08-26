package de.playground.controller;

import de.playground.dtos.User;
import de.playground.security.AccessController;
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

public class AccessControllerTests {
    private IUserService userService;
    private IAccessUtils accessUtils;
    private AccessController controller;

    @BeforeEach
    void setUp() {
        this.userService = Mockito.mock(IUserService.class);
        this.controller = new AccessController(userService, accessUtils);
    }

    @Test
    @DisplayName("Create user works")
    void createUserTest() {
        // arrange
        var user = new User("username", "123");

        // act
        var response = this.controller.createUser(user);

        // assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("create user returns 400 if user is null")
    void createNullUserTest() {
        // act
        var response = this.controller.createUser(null);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User is invalid.", response.getBody());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @EmptySource
    @DisplayName("create user returns 400 if username is null or empty")
    void createUserWithInvalidUsernameTest(String username) {
        // arrange
        var user = new User(username, "123");

        // act
        var response = this.controller.createUser(user);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User is invalid.", response.getBody());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "  "})
    @EmptySource
    @DisplayName("create user returns 400 if password is null or empty")
    void createUserWithInvalidPasswordTest(String password) {
        // arrange
        var user = new User("hugo", password);

        // act
        var response = this.controller.createUser(user);

        // assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("User is invalid.", response.getBody());
    }
}
