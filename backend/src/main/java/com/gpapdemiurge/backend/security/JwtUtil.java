package com.gpapdemiurge.backend.security;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Utility component responsible for creating and validating JSON Web Tokens (JWTs).
 *
 * <p>Tokens carry the authenticated principal's username and role(s) in their
 * claims. The signing key and expiration time are configured externally via
 * {@code application.properties} so they can differ between environments
 * (development, CI, production) without recompiling.
 */
@Component
public class JwtUtil {

    private final Key signingKey;
    private final long jwtExpirationMs;

    /**
     * @param secret          base64-encoded secret string used to sign tokens
     * @param jwtExpirationMs how long (milliseconds) a token stays valid
     */
    public JwtUtil(@Value("${app.jwt.secret}") String secret,
                   @Value("${app.jwt.expiration-ms:86400000}") long jwtExpirationMs) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtExpirationMs = jwtExpirationMs;
    }

    /**
     * Builds a signed JWT containing the principal's username and roles.
     *
     * @param authentication the successful {@link Authentication} object
     * @return a compact JWT string
     */
    public String generateToken(Authentication authentication) {
        var userDetails = (org.springframework.security.core.userdetails.UserDetails)
                authentication.getPrincipal();

        String authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("authorities", authorities)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates that the given token is well-formed, correctly signed, and not expired.
     *
     * @param token the raw JWT string extracted from the {@code Authorization} header
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Invalid signature, expired token, malformed token, etc.
            return false;
        }
    }

    /** @return the username (subject) embedded in the token, or null if invalid. */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}
