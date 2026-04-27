package com.cultureconnect.programgrant.dto;

import com.cultureconnect.programgrant.enums.Status;

import lombok.Data;

@Data
public class GrantApprovalDto {
    private Status status; // Should be APPROVED
    private Double approvedAmount;
}
