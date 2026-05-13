package com.cultureconnect.reportandanalysis.dto;

import lombok.Data;

@Data
public class GrantDTO {
    private Long applicationId;
    private String citizenName;
    private String programName;
    private String status;
    private Double grantAmount;
    private String submittedDate;
}