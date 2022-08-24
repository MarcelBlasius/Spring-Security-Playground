package de.playground.database;

import de.playground.dtos.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@AllArgsConstructor
public class UserInMemoryRepository implements IUserRepository {
    private Map<String, User> users;

    @Override
    public void createUser(User user) {
        if (!this.users.containsKey(user.getUsername())) {
            this.users.put(user.getUsername(), user);
        }
    }

    @Override
    public User readUser(String username) {
        return this.users.getOrDefault(username, null);
    }

    @Override
    public void updateUser(User user) {
        this.users.replace(user.getUsername(), user);
    }

    @Override
    public void deleteUser(String username) {
        this.users.remove(username);
    }
}
