package com.cultureconnect.programgrant.dto;

import java.time.LocalDateTime;

import com.cultureconnect.programgrant.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for displaying Grant Application details.
 * Synchronized with Entity types (LocalDateTime) and naming (programName).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor // Added for JSON deserialization support
public class GrantApplicationResponseDto {
    private Long applicationId;
    private Long citizenId;
    private Long programId;
    private String programName; // Changed from programTitle to match Entity
    private LocalDateTime submittedDate; // Changed from LocalDate to match Entity
    private Status status;
    private Double grantAmount;
    
    private String citizenName;
    private String citizenEmail;
    private Double programBudget;
    private Double remainingBudget;


}