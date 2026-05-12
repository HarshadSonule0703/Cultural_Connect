package com.cultureconnect.programgrant.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CulturalProgramResponseCitizenDto {
 
	private Long programId;
	private String title;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	@JsonProperty("budget")
	private Double budget;
 
}