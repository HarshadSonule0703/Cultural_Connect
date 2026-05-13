package com.example.demo.dto;


import java.time.LocalDateTime;

import com.example.demo.enums.Status;

import lombok.Data;

@Data
public class GrantApplicationResponseDto {

    private Long applicationId;
    private Long citizenId;
    private Long programId;
    private String programName;
    private LocalDateTime submittedDate;
    private Status status;
    private Double grantAmount;
}
