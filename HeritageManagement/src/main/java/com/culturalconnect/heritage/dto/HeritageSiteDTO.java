package com.culturalconnect.heritage.dto;


import jakarta.validation.constraints.*;
 
public class HeritageSiteDTO {
 
    @NotBlank
    private String name;
 
    @NotBlank
    private String location;
 
    @Size(max = 500)
    private String description;
 
    @NotBlank
    private String status;
 
    public HeritageSiteDTO() {}
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getLocation() {
        return location;
    }
 
    public void setLocation(String location) {
        this.location = location;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
}
 