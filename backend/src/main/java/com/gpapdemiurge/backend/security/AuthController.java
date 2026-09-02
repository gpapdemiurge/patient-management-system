package com.gpapdemiurge.backend.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gpapdemiurge.backend.security.dto.JwtResponse;
import com.gpapdemiurge.backend.security.dto.LoginRequest;

/**
 * REST controller exposing a single {@code POST /api/auth/login} endpoint.
 *
 * <p>This is the entry point for all authentication. It accepts username and
 * password, delegates credential verification to
 * {@link AuthenticationService}, and returns a signed JWT together with the
 * user's role so the frontend can route the user to the appropriate panel.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticates the user and returns a JWT.
     *
     * <p>Endpoint: {@code POST /api/auth/login}
     *
     * @param request the login payload ({@code username} + {@code password})
     * @return {@code 200 OK} with a {@link JwtResponse} containing the token,
     *         username, and role
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        JwtResponse jwtResponse = authenticationService.authenticate(request);
        return ResponseEntity.ok(jwtResponse);
    }
}
