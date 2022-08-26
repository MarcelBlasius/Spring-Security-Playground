package de.playground.controller;

import de.playground.dtos.User;
import de.playground.security.IAccessUtils;
import de.playground.services.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final IUserService userService;
    private final IAccessUtils accessService;

    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if (user == null || user.IsInvalid()) {
            return ResponseEntity.badRequest().body("User is invalid.");
        }

        if (!accessService.authorizeUser(user.getUsername())) {
            return ResponseEntity.status(403).build();
        }

        this.userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is invalid.");
        }

        if (!accessService.authorizeUser(username)) {
            return ResponseEntity.status(403).build();
        }


        this.userService.deleteUser(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> readUser(@PathVariable String username) {
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is invalid.");
        }

        if (!accessService.authorizeUser(username)) {
            return ResponseEntity.status(403).build();
        }

        var user = this.userService.readUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(user);
    }
}
