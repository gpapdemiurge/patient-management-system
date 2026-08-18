package com.gpapdemiurge.backend.entity;

/**
 * Lifecycle status of an {@link Appointment}.
 * Stored as a VARCHAR(50) string in the database (see Flyway V1).
 * Default value on insert is {@link #SCHEDULED}.
 */
public enum AppointmentStatus {
    SCHEDULED,
    CONFIRMED,
    COMPLETED,
    CANCELLED,
    NO_SHOW
}
