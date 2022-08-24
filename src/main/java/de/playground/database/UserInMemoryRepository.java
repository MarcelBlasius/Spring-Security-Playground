package de.playground.database;

import de.playground.dtos.User;
import lombok.AllArgsConstructor;
import java.util.Map;

@AllArgsConstructor
public class UserInMemoryRepository implements UserRepository{
    private Map<String, User> users;

    @Override
    public void createUser(User user) {
        if (!users.containsKey(user.getUsername())) {
            users.put(user.getUsername(), user);
        }
    }

    @Override
    public User readUser(String username) {
        return users.getOrDefault(username, null);
    }

    @Override
    public void updateUser(User user) {
        users.replace(user.getUsername(), user);
    }

    @Override
    public void deleteUser(String username) {
        users.remove(username);
    }
}
