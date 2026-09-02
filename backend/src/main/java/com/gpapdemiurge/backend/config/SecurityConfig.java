package com.gpapdemiurge.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gpapdemiurge.backend.security.filter.JwtAuthenticationFilter;

/**
 * Central Spring Security configuration.
 *
 * <p>Sets up stateless JWT authentication:
 *
 * <ol>
 *   <li>{@code /api/auth/**} and {@code /v3/api-docs/**} are public (no
 *   auth required).</li>
 *   <li>Every other request requires authentication.</li>
 *   <li>Session management is {@link SessionCreationPolicy#STATELESS} so no
 *   server-side session is created – each request must carry a valid JWT.</li>
 *   <li>CSRF is disabled (intended for a stateless JWT‑based API).</li>
 *   <li>{@link JwtAuthenticationFilter} is inserted before the default
 *   {@link UsernamePasswordAuthenticationFilter} to validate tokens.</li>
 * </ol>
 *
 * <p>{@link PasswordEncoder} uses BCrypt, which is the recommended choice for
 * storing user passwords securely.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Defines the public vs. protected endpoints and wires in the JWT filter.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults()) // for Swagger UI / testing
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /** BCrypt-based password encoder for securely hashing user passwords. */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Exposes the {@link AuthenticationManager} so that
     * {@link com.gpapdemiurge.backend.security.AuthenticationService} can
     * perform authenticated authentication attempts.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
