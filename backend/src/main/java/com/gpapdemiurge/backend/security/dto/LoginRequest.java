package com.gpapdemiurge.backend.security.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request body for the {@code POST /api/auth/login} endpoint.
 *
 * <p>Both fields are required; the JSON validation framework ({@code @Valid})
 * rejects requests that omit either username or password.
 */
public class LoginRequest {

    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
