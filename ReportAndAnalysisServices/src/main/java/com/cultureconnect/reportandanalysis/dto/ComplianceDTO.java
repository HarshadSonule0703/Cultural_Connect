package com.cultureconnect.reportandanalysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ComplianceDTO {
    
    @JsonProperty("complianceId") // Maps the incoming JSON 'complianceId' to 'id'
    private Long id;

    @JsonProperty("result")       // Maps the incoming JSON 'result' to 'status'
    private String status; 
}