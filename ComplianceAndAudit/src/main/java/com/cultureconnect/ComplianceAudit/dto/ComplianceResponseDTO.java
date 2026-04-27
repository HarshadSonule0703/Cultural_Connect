
package com.cultureconnect.ComplianceAudit.dto;

import java.time.LocalDate;

import com.cultureconnect.ComplianceAudit.enums.ComplianceType;

import lombok.Data;

@Data
public class ComplianceResponseDTO {
    private Long complianceId;
    private Long entityId;
    private ComplianceType type;
    private String result;
    private LocalDate date;
    private String notes;
}