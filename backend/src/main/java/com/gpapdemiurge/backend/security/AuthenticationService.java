package com.gpapdemiurge.backend.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.gpapdemiurge.backend.entity.Role;
import com.gpapdemiurge.backend.repository.UserRepository;
import com.gpapdemiurge.backend.security.dto.JwtResponse;
import com.gpapdemiurge.backend.security.dto.LoginRequest;

/**
 * Handles authentication logic for the {@code /api/auth/login} endpoint.
 *
 * <p>The service uses Spring Security's {@link AuthenticationManager} to verify
 * the supplied credentials, then produces a signed JWT via {@link JwtUtil}.
 * The JWT (along with the username and the user's role) is returned to the
 * caller so the frontend can store it and redirect the user to the correct
 * panel based on their role.
 */
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtUtil jwtUtil,
                                 UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    /**
     * Attempts to authenticate the given username/password pair.
     *
     * @param request the request containing username and password
     * @return a {@link JwtResponse} containing the token, username, and role
     * @throws AuthenticationException if the credentials are invalid
     */
    public JwtResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(authentication);

        // Resolve the user's role for the response so the frontend can
        // redirect ADMINs and DOCTORs to the correct panel.
        Role role = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new AuthenticationException(
                        "Authenticated user no longer exists in the database") {})
                .getRole();

        return new JwtResponse(token, userDetails.getUsername(), role.name());
    }
}
