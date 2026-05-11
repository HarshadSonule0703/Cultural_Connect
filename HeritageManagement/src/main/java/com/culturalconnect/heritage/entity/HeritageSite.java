package com.culturalconnect.heritage.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
 
@Entity
@Table(name = "heritage_sites")
public class HeritageSite {
 
    @Id
    @Column(length = 36)
    private String siteId;
 
    @NotBlank(message = "Site name cannot be empty")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Column(nullable = false)
    private String name;
 
    @NotBlank(message = "Location cannot be empty")
    @Column(nullable = false)
    private String location;
 
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
 
    @NotBlank(message = "Status is required")
    private String status;
    
//    @NotBlank(message = "File is required")
    private String fileUri;
 
    
    @OneToMany(mappedBy = "heritageSite",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<PreservationActivity> activities;
 
    public HeritageSite() {
    }
 
    public String getFileUri() {
        return fileUri;
    }
    
    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
 
    public String getSiteId() {
        return siteId;
    }
 
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
 
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
 
//    public List<PreservationActivity> getActivities() {
//        return activities;
//    }
// 
//    public void setActivities(List<PreservationActivity> activities) {
//        this.activities = activities;
//    }
}
 