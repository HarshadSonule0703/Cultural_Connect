package com.cultureconnect.reportandanalysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventDTO {
    // Tells Feign: "Take the 'title' from their JSON and put it in my 'name' variable"
    @JsonProperty("title") 
    private String name; 
    
    private Double rating;
}