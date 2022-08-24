package de.playground.controller;

import de.playground.dtos.User;
import de.playground.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user == null || user.IsInvalid()) {
            return ResponseEntity.badRequest().body("User is invalid.");
        }

        this.userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if (user == null || user.IsInvalid()) {
            return ResponseEntity.badRequest().body("User is invalid.");
        }

        this.userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is invalid.");
        }

        this.userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> readUser(@PathVariable String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is invalid.");
        }

        var user = this.userService.readUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }
}
