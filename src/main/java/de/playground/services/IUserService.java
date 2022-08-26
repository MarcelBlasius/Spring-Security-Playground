package de.playground.services;

import de.playground.dtos.User;

public interface IUserService {
    void createUser(User user);

    User readUser(String username);

    void updateUser(User user);

    void deleteUser(String username);
}
