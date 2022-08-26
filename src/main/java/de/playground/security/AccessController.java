package de.playground.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.playground.dtos.User;
import de.playground.services.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@Slf4j
public class AccessController {
    private final IUserService userService;
    private final IAccessUtils accessUtils;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user == null || user.IsInvalid()) {
            return ResponseEntity.badRequest().body("User is invalid.");
        }

        this.userService.createUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Refresh token is missing");
        }

        try {
            var refreshToken = authorizationHeader.substring("Bearer ".length());
            var username = accessUtils.decodeUsername(refreshToken);
            var user = userService.readUser(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            var accessToken = accessUtils.createAccessToken(username, request.getRequestURL().toString());

            var tokens = new HashMap<String, String>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);

            new ObjectMapper().writeValue(response.getOutputStream(), tokens);

        } catch (Exception e) {
            accessUtils.sendErrorResponse(response, e.getMessage());
        }
    }
}
