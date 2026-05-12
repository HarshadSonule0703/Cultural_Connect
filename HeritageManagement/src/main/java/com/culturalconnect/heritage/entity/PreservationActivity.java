package com.culturalconnect.heritage.entity;
 
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
 
@Entity
@Table(name = "preservation_activities")
public class PreservationActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;
    
    private String siteId;
    
    @NotBlank(message = "Activity description cannot be empty")
    @Size(max = 500)
    private String description;
 
    @NotNull(message = "Activity date is required")
    private LocalDate date;
 
    @NotBlank(message = "Status cannot be empty")
    private String status;

    @NotNull(message = "Officer ID cannot be null")
    private Long officerId;
 
    // Many Activities -> One Heritage Site
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "siteId", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private HeritageSite heritageSite;
 
    public PreservationActivity() {
    }
 
    // Getters and Setters
 
    public Long getActivityId() {
        return activityId;
    }
 
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
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
 
    public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public void setStatus(String status) {
        this.status = status;
    }
 
    public Long getOfficerId() {
        return officerId;
    }
 
    public void setOfficerId(Long officerId) {
        this.officerId = officerId;
    }
 
//    public HeritageSite getHeritageSite() {
//        return heritageSite;
//    }
// 
//    public void setHeritageSite(HeritageSite heritageSite) {
//        this.heritageSite = heritageSite;
//    }
}
 