package de.playground.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.playground.services.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Slf4j
@AllArgsConstructor
public class AccessUtils implements IAccessUtils, UserDetailsService {
    private IUserService userService;

    @Override
    public boolean authorizeUser(String username) {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(username);
    }

    @Override
    public void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException {
        log.error("Error log in in: {}", errorMessage);
        response.setHeader("error", errorMessage);
        response.setStatus(SC_FORBIDDEN);
        var errors = new HashMap<String, String>();
        errors.put("error_message", errorMessage);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), errors);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userService.readUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
