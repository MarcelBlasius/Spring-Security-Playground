package de.playground.security;

import de.playground.dtos.User;
import de.playground.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccessUtilsTests {
    private IUserService userService;
    private AccessUtils accessUtils;

    @BeforeEach
    void setUp() {
        this.userService = Mockito.mock(IUserService.class);
        this.accessUtils = new AccessUtils(this.userService);
    }

    @Test
    @DisplayName("Load user by username works")
    void loadUserByUsernameTest() {
        // arrange
        Mockito.when(this.userService.readUser("user")).thenReturn(new User("user", "123"));

        // act
        var result = this.accessUtils.loadUserByUsername("user");

        // assert
        assertEquals("user", result.getUsername());
        assertEquals("123", result.getPassword());
        assertEquals(0, result.getAuthorities().size());
    }

    @Test
    @DisplayName("Load user by username throws UserNotFoundException if user is not found")
    void loadUserByUsernameNotFoundTest() {
        // arrange
        Mockito.when(this.userService.readUser("user")).thenReturn(null);

        // act & assert
        assertThrows(UsernameNotFoundException.class, () -> this.accessUtils.loadUserByUsername("user"));
    }
}
