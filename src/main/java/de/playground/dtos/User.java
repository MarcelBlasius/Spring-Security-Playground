package de.playground.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {
    private String username;
    private String password;

    public boolean IsInvalid() {
        return this.username == null || this.username.trim().isEmpty() || this.password == null || this.password.trim().isEmpty();
    }
}
