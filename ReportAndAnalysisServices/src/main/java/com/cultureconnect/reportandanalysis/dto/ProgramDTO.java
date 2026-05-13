package com.cultureconnect.reportandanalysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProgramDTO {
    @JsonProperty("programId")
    private Long id;
    
    @JsonProperty("title") 
    private String name;
    
    private String description;
    private String status;
    private Double budget;
    private String startDate;
    private String endDate;
}