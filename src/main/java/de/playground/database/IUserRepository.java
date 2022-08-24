package de.playground.database;

import de.playground.dtos.User;

public interface IUserRepository {
    void createUser(User user);

    User readUser(String username);

    void updateUser(User user);

    void deleteUser(String username);
}
