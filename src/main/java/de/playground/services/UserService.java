package de.playground.services;

import de.playground.database.IUserRepository;
import de.playground.dtos.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private final IUserRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(User user) {
        log.info("Creating user with username {} and password {}", user.getUsername(), user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.repo.createUser(user);
    }

    @Override
    public User readUser(String username) {
        return this.repo.readUser(username);
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.repo.updateUser(user);
    }

    @Override
    public void deleteUser(String username) {
        this.repo.deleteUser(username);
    }
}
