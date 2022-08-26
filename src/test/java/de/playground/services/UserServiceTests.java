package de.playground.services;

import de.playground.database.IUserRepository;
import de.playground.dtos.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTests {
    private IUserRepository repo;
    private UserService service;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.repo = Mockito.mock(IUserRepository.class);
        this.passwordEncoder = Mockito.mock(PasswordEncoder.class);
        this.service = new UserService(repo, passwordEncoder);
    }

    @Test
    @DisplayName("create user works")
    void createUserTest() {
        // arrange
        var user = new User("hugo", "123");

        // act
        this.service.createUser(user);

        // assert
        verify(repo, times(1)).createUser(user);
    }

    @Test
    @DisplayName("read user works")
    void readUserTest() {
        // act
        this.service.readUser("username");

        // assert
        verify(repo, times(1)).readUser("username");
    }

    @Test
    @DisplayName("update user works")
    void updateUserTest() {
        // arrange
        var user = new User("hugo", "123");

        // act
        this.service.updateUser(user);

        // assert
        verify(repo, times(1)).updateUser(user);
    }

    @Test
    @DisplayName("delete user works")
    void deleteUserTest() {
        // act
        this.service.deleteUser("username");

        // assert
        verify(repo, times(1)).deleteUser("username");
    }
}
