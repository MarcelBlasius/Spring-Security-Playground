package de.playground.security;

public interface ITokenService {
    String decodeUsername(String token);

    String createAccessToken(String username, String requestUrl);

    String createRefreshTokenToken(String username, String requestUrl);
}
