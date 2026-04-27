package com.cultureconnect.ComplianceAudit.dto;

import com.cultureconnect.ComplianceAudit.enums.ComplianceType;

import lombok.Data;

@Data
public class ComplianceRequestDTO {
    private Long entityId;
    private ComplianceType type;
    private String notes;
    private String result;
}

