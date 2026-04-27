package com.culturalconnect.heritage.dto;
 
import jakarta.validation.constraints.*;
import java.time.LocalDate;
 
public class PreservationActivityDTO {
 
    @NotNull
    private Long siteId;
 
    @NotNull
    private Long officerId;
 
    @NotBlank
    private String description;
 
    @NotNull
    private LocalDate date;
 
    @NotBlank
    private String status;
 
    public PreservationActivityDTO(){}
 
    public Long getSiteId() {
        return siteId;
    }
 
    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
 
    public Long getOfficerId() {
        return officerId;
    }
 
    public void setOfficerId(Long officerId) {
        this.officerId = officerId;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
 
    public LocalDate getDate() {
        return date;
    }
 
    public void setDate(LocalDate date) {
        this.date = date;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
}
 