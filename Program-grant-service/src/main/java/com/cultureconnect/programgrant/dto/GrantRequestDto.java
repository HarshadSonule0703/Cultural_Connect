package com.cultureconnect.programgrant.dto;

import java.time.LocalDate;

import com.cultureconnect.programgrant.enums.Status;

import lombok.Data;

@Data
public class GrantRequestDto {
	private Long programId;
	private Long citizenId;
	private Double amount;
	private LocalDate date = LocalDate.now();
	private Status status = Status.ACTIVE;
}
