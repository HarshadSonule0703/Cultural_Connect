package com.cultureconnect.reportandanalysis.dto;

import lombok.Data;

@Data
public class ComplianceDTO {
    private Long id;
    private String status; // This will catch your "VERIFIED", "PENDING", etc.
}