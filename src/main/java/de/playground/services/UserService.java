package de.playground.services;

import de.playground.database.IUserRepository;
import de.playground.dtos.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository repo;
    
    @Override
    public void createUser(User user) {
        this.repo.createUser(user);
    }

    @Override
    public User readUser(String username) {
        return this.repo.readUser(username);
    }

    @Override
    public void updateUser(User user) {
        this.repo.updateUser(user);
    }

    @Override
    public void deleteUser(String username) {
        this.repo.deleteUser(username);
    }
}
