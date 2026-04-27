package com.cultureconnect.ComplianceAudit.dto;
import java.time.LocalDate;

import com.cultureconnect.ComplianceAudit.enums.Status;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AuditRequestDTO {
    @NotBlank(message = "Scope is mandatory")
    private String scope;
    private String findings;
    @NotNull(message = "Date is required")
    private LocalDate date;
    @NotNull(message = "Status is required")
    private Status status;
    @NotNull(message = "Officer ID is required")
    private Long officerId; // References User Service
}