package de.playground.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomAuthenticationFilterTests {
    private AuthenticationManager authenticationManager;
    private IAccessUtils accessUtils;
    private CustomAuthenticationFilter filter;
    private ITokenService tokenService;

    @BeforeEach
    void setUp() {
        this.authenticationManager = Mockito.mock(AuthenticationManager.class);
        this.accessUtils = Mockito.mock(IAccessUtils.class);
        this.tokenService = Mockito.mock(ITokenService.class);
        filter = new CustomAuthenticationFilter(this.authenticationManager, this.accessUtils, this.tokenService);
    }

    @Test
    @DisplayName("Attempt authentication works")
    void attemptAuthenticationTest() {
        // arrange
        var request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getParameter("username")).thenReturn("username");
        Mockito.when(request.getParameter("password")).thenReturn("password");
        var expectedToken = new UsernamePasswordAuthenticationToken("username", "password");

        // act
        this.filter.attemptAuthentication(request, Mockito.mock(HttpServletResponse.class));

        // assert
        Mockito.verify(this.authenticationManager, Mockito.times(1)).authenticate(expectedToken);
    }
}
