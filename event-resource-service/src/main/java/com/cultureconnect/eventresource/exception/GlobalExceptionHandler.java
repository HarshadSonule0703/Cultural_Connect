package com.cultureconnect.eventresource.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.cultureconnect.eventresource.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

/**
 * The GlobalExceptionHandler provides a centralized mechanism to intercept and handle 
 * exceptions thrown across the entire application. It ensures that the API always 
 * returns a consistent JSON error structure (ErrorResponse) instead of a raw stack trace.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles specific business logic failures where a resource (Citizen, Program, Application) 
     * is not found in the database.
     * Triggered by: .orElseThrow(() -> new ResourceNotFoundException(...)) in Services.
     */
   // @ExceptionHandler(ResourceNotFoundException.class) // Changed from RuntimeException for specificity
   // public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
      //  ErrorResponse error = new ErrorResponse(
      //      LocalDateTime.now(), 
      //      HttpStatus.NOT_FOUND.value(), 
       //     "Resource Not Found",
     //       ex.getMessage(), 
      //      request.getRequestURI()
    //    );
   //     return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    //}

    /**
     * Handles cases where a user provides an invalid value for a path variable or request parameter.
     * Example: Passing "CANCELLED" in the URL when the Status Enum only accepts "PENDING/APPROVED".
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {
        String message = String.format(
                "The parameter '%s' has an invalid value. Expected values are from the valid Enum constants.", ex.getName());
        
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
     * Handles Bean Validation failures triggered by @Valid in the Controller.
     * Example: If @NotBlank(message = "Title is required") fails, this method catches it.
     */
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            org.springframework.web.bind.MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Extracts the custom message defined in the DTO (e.g., "Budget must be greater than zero")
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.BAD_REQUEST.value(),
            "Validation Failed", 
            errorMessage, 
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles Database-level errors such as Unique Constraint violations.
     * Example: Trying to register a Citizen with an Email that already exists in the system.
     */
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConflict(org.springframework.dao.DataIntegrityViolationException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.CONFLICT.value(), 
            "Database Integrity Conflict",
            "The operation could not be completed because it violates database constraints (e.g., duplicate entry).", 
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * The final 'Catch-All' handler. If an error occurs that isn't specifically handled 
     * by the methods above, this method returns a 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(), 
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Internal System Error", 
            "An unexpected error occurred: " + ex.getMessage(), 
            request.getRequestURI()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<String> handleInvalidCredentials(InvalidCredentialsException ex){
		return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
	}
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            jakarta.validation.ConstraintViolationException ex,
            HttpServletRequest request) {
     
        String message = ex.getConstraintViolations()
                .iterator()
                .next()
                .getMessage();
     
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                message,   // ✅ SHORT MESSAGE
                request.getRequestURI()
        );
     
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
}

