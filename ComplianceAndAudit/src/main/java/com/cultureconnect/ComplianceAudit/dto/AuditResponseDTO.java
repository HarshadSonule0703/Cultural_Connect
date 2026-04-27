package com.cultureconnect.ComplianceAudit.dto;
import java.time.LocalDate;

import com.cultureconnect.ComplianceAudit.enums.Status;

import lombok.Data;

@Data
public class AuditResponseDTO {
    private Long auditId;
    private String scope;
    private String findings;
    private LocalDate date;
    private Status status;
    private String officerName; // Resolved via Feign
}