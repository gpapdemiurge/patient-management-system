package com.gpapdemiurge.backend.security.dto;

/**
 * Response body for the {@code POST /api/auth/login} endpoint.
 *
 * <p>Wraps the JWT inside a {@code "token"} field and includes the user's
 * role so the frontend knows which panel to redirect to after login.
 */
public class JwtResponse {

    private String token;
    private String tokenType = "Bearer";
    private String username;
    private String role;

    public JwtResponse() {
    }

    public JwtResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
