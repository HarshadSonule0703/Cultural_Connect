package com.cultureconnect.programgrant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * CUSTOM EXCEPTION: This exception is thrown whenever a database search fails 
 * to find a specific record (Citizen, Report, User, etc.).
 * * @ResponseStatus: Ensures that if this exception escapes the Global Handler, 
 * it still returns a 404 Not Found instead of a 500 Internal Error.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    // Unique version ID for serialization (Satisfies @SuppressWarnings("serial"))
    private static final long serialVersionUID = 1L;

    /**
     * Constructor that accepts a custom error message.
     * @param message The specific error detail (e.g., "User not found with ID 5")
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}