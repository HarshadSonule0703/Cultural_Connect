package com.cultureconnect.ComplianceAudit.dto;

import java.time.LocalDate;
import java.util.List;

import com.cultureconnect.ComplianceAudit.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CulturalProgramResponseDto {

	private Long programId;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private Double budget;
	private Status status;

}
