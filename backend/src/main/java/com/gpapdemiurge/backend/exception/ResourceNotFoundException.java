package com.gpapdemiurge.backend.exception;

/**
 * Thrown when a client attempts to operate on an entity (or perform an action)
 * that does not yet exist in the database.
 *
 * <p>This is a checked-free {@link RuntimeException} so it bubbles up through
 * the service layer and is translated into a {@code 404 Not Found} by the
 * global exception handler ({@code RestExceptionHandler}).
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new exception with the given detail message.
     *
     * @param message the detail message (typically the type of entity and the
     *                id that was not found, e.g.
     *                {@code "User not found with id: 5"})
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
