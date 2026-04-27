package com.cultureconnect.reportandanalysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProgramDTO {
    @JsonProperty("title") // (Assuming Program also uses 'title'. If it uses 'name', you can remove this!)
    private String name;
    
    private Double budget;
}