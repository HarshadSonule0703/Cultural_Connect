package com.cultureconnect.programgrant.dto;

import java.time.LocalDate;

import com.cultureconnect.programgrant.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GrantResponseDto {
    private Long grantId;
    private String programTitle;
    private String citizenName;
    private Double amount;
    private LocalDate date;
    private Status status;
}
