package com.example.demo.exception;
 
import java.util.HashMap;
import java.util.Map;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import org.springframework.web.bind.MethodArgumentNotValidException;
 
@RestControllerAdvice
public class GlobalExceptionHandler {
 
    // Handle Resource Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
 
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
 
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
 
    // Handle Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
 
        Map<String, String> errors = new HashMap<>();
 
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
 
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
 
    // Handle General Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
 
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
 
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}