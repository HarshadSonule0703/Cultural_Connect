package com.cultureconnect.programgrant.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cultureconnect.programgrant.dto.ErrorResponse;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * GLOBAL EXCEPTION HANDLER: Centralized interceptor for all API errors.
 * Ensures the frontend always receives a structured JSON response instead of a stack trace.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404 NOT FOUND: Handles cases where a requested resource does not exist.
     * Intercepts both custom ResourceNotFoundException and standard JPA EntityNotFoundException.
     */
    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(Exception ex, HttpServletRequest request) {
        log.error("Resource not found: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.NOT_FOUND.value(), 
            "Resource Not Found",
            ex.getMessage(), 
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * 400 VALIDATION ERROR: Triggered by @Valid in Controllers.
     * Merged logic: Now extracts ALL field validation errors into a single string.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        String allErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        log.warn("Validation failed for request {}: {}", request.getRequestURI(), allErrors);

        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.BAD_REQUEST.value(),
            "Validation Failed", 
            allErrors, 
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 TYPE MISMATCH: Handles invalid URL parameters or Enum mismatches.
     * Example: Passing 'TEXT' to a field that expects an Integer ID.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {
        
        String message = String.format("The parameter '%s' has an invalid value '%s'. Expected a different data type.", 
                ex.getName(), ex.getValue());
        
        log.warn("Type mismatch error: {}", message);

        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.BAD_REQUEST.value(), 
            "Invalid Input Format",
            message, 
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * 409 CONFLICT: Handles database constraint violations.
     * Example: Trying to use an email that is already registered (Unique Constraint).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConflict(DataIntegrityViolationException ex,
            HttpServletRequest request) {
        
        log.error("Database conflict at {}: {}", request.getRequestURI(), ex.getMessage());

        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.CONFLICT.value(), 
            "Database Integrity Conflict",
            "This operation violates database constraints (e.g., duplicate entry or foreign key restriction).", 
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * 401/400 AUTH ERROR: Specifically for login failures.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex, HttpServletRequest request){
        log.warn("Unauthorized access attempt: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(), 
                HttpStatus.UNAUTHORIZED.value(), 
                "Authentication Failed",
                ex.getMessage(), 
                request.getRequestURI()
            );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 500 INTERNAL SERVER ERROR: The final catch-all for any unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        log.error("CRITICAL SYSTEM FAILURE at {}: ", request.getRequestURI(), ex);
        
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal System Error", 
            "An unexpected server-side error occurred. Please contact support.", 
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}