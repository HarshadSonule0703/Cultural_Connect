package com.cultureconnect.reportandanalysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class EventDTO {
    @JsonProperty("eventId")
    private Long id;
    
    @JsonProperty("title") 
    private String name;
    
    private String location;
    private String date;
    private String status;
}