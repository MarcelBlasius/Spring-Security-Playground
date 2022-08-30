package de.playground.security;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IAccessUtils {

    boolean authorizeUser(String username);

    void sendErrorResponse(HttpServletResponse response, String errorMessage) throws IOException;
}
