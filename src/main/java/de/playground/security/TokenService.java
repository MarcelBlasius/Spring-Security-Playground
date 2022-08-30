package de.playground.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService implements ITokenService {

    public String createAccessToken(String username, String requestUrl) {
        var hashingAlgorithm = GetHashingAlgorithm();
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .withIssuer(requestUrl)
                .sign(hashingAlgorithm);
    }

    @Override
    public String createRefreshTokenToken(String username, String requestUrl) {
        var hashingAlgorithm = GetHashingAlgorithm();
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .withIssuer(requestUrl)
                .sign(hashingAlgorithm);
    }

    @Override
    public String decodeUsername(String token) {
        var algorithm = Algorithm.HMAC256("secret".getBytes());
        var verifier = JWT.require(algorithm).build();
        var decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

    private static Algorithm GetHashingAlgorithm() {
        return Algorithm.HMAC256("secret".getBytes());
    }
}
