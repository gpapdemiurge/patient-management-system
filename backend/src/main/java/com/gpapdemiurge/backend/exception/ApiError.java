package com.gpapdemiurge.backend.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Serializable error payload returned by {@link RestExceptionHandler} for every
 * error response.
 *
 * <p>Keeping a single error structure means API consumers always know where to
 * find the timestamp, HTTP status, reason, and message regardless of which
 * exception triggered the response.
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiError {

    /** When the error occurred. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime timestamp;

    /** HTTP status code (e.g. {@code 404}). */
    private int status;

    /** Short HTTP reason phrase (e.g. {@code "Not Found"}). */
    private String error;

    /** Human-readable detail message. */
    private String message;

    /** The request path (may be null for validation errors). */
    private String path;

    /**
     * Whether additional debug info should be exposed. Kept {@code false} in
     * production to avoid leaking internal details.
     */
    private boolean debug = false;

    public ApiError(LocalDateTime timestamp,
                    int status,
                    String error,
                    String message,
                    String path,
                    boolean debug) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.debug = debug;
    }
}
