package com.gpapdemiurge.backend.entity;

/**
 * Role assigned to a {@link User} in the hospital system.
 * Stored as a VARCHAR(50) string in the database (see Flyway V1).
 */
public enum Role {
    ADMIN,
    DOCTOR,
    NURSE,
    RECEPTIONIST
}
