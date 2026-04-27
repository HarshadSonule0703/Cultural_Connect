package com.cultureconnect.programgrant.dto;

import lombok.Data;

/**
 * DTO for submitting a new Grant Application.
 * Only requires the IDs of the Citizen and the Program.
 */
@Data
public class GrantApplicationRequestDto {
    private Long citizenId;
    private Long programId;
    // status and submittedDate are managed by the System/Database
}