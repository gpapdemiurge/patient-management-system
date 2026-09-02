package com.gpapdemiurge.backend.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Centralized error handling for the REST API.
 *
 * <p>Maps exceptions thrown from the service layer into the appropriate HTTP
 * status codes and a consistent JSON error body, so the controllers never need
 * to repeat try/catch boilerplate.
 */
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * Translates a {@link ResourceNotFoundException} (e.g. looking up a
     * patient/user/appointment that does not exist) into a {@code 404 Not
     * Found}.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex,
                                                   WebRequest request) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                determinePath(request),
                false
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Translates invalid input (e.g. from {@code @Valid} checks) into a
     * {@code 400 Bad Request}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex,
                                                     WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message,
                null,
                false
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Fallback for any other unhandled exception so we always return a clean
     * JSON error body instead of a raw stack trace (configurable in
     * {@code application.properties}).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, WebRequest request) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                determinePath(request),
                false
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** @return the request path from the {@link WebRequest}, or {@code null} if unavailable. */
    private String determinePath(WebRequest request) {
        try {
            Object raw = request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinApplication",
                                              WebRequest.SCOPE_REQUEST);
            return raw != null ? raw.toString() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
