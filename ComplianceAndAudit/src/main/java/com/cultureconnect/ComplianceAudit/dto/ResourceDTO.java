package com.cultureconnect.ComplianceAudit.dto;

import com.cultureconnect.ComplianceAudit.enums.ResourceType;
import com.cultureconnect.ComplianceAudit.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDTO {

    private Long resourceId;
    private ResourceType type;
    private Integer quantity;
    private Status status;
    
    // Flattened reference: just the ID, not the whole object
    private Long eventId; 
}