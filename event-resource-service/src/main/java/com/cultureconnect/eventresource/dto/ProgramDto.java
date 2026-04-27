package com.cultureconnect.eventresource.dto;

import java.time.LocalDate;

import com.cultureconnect.eventresource.enums.Status;
 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDto {
	private Long programId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    private Status status;
}
