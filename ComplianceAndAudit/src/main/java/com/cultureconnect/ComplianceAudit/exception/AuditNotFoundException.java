package com.cultureconnect.ComplianceAudit.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditNotFoundException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(AuditNotFoundException.class);

    public AuditNotFoundException(String message) {
        super(message);
        logger.error("AuditNotFoundException thrown: {}", message);
    }
}
