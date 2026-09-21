package com.gpapdemiurge.backend.controller;

import com.gpapdemiurge.backend.security.JwtUtil;
import com.gpapdemiurge.backend.service.AuthService;
import com.gpapdemiurge.backend.entity.User;
import com.gpapdemiurge.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtils;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, JwtUtil jwtUtils, UserRepository userRepository) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        // authenticate and return JWT
        String username = authService.authenticate(request.getUsername(), request.getPassword());
        String token = jwtUtils.generateTokenFromUsername(username);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @org.springframework.web.bind.annotation.GetMapping("/me")
    public ResponseEntity<UserProfile> me(org.springframework.security.core.Authentication authentication,
                                          @org.springframework.web.bind.annotation.RequestHeader(value = "Authorization", required = false) String authHeader) {
        String username = null;

        if (authentication != null && authentication.isAuthenticated()) {
            username = authentication.getName();
        }

        if (username == null && authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtils.validateToken(token)) {
                username = jwtUtils.getUsernameFromToken(token);
            } else {
                return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
            }
        }

        if (username == null) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = new UserProfile(user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(profile);
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    public static class LoginResponse {
        private final String token;
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    public static class UserProfile {
        private Long id;
        private String username;
        private String email;
        private String role;
    }
}
