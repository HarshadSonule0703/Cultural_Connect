package com.cultureconnect.ComplianceAudit.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AuditGlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuditGlobalExceptionHandler.class);

    @ExceptionHandler(AuditNotFoundException.class)
    public String handleAuditNotFound(AuditNotFoundException ex) {
        logger.error("Audit not found exception: {}", ex.getMessage());
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidation(MethodArgumentNotValidException ex) {
        logger.warn("Validation error occurred: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            logger.debug("Validation failed for field: {} with message: {}", error.getField(), error.getDefaultMessage());
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return errors;
    }
}
