package com.cultureconnect.ComplianceAudit.entity;

import java.time.LocalDate;
 
import com.cultureconnect.ComplianceAudit.enums.Status;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
/**
* ENTITY: CulturalProgram represents the core initiatives of the organization.
* It acts as a parent for Grants, Events, and Applications.
*/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "new_programs")
public class NewProgram {
 
    @Id
    private Long programId;
 
    @NotBlank(message = "Program name is required")
    @Column(nullable = false)
    private String name; // Standardized to 'name' to match your Reporting Service logic
 
    @NotBlank(message = "Program description is required")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
 
    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be today or in the future")
    @Column(nullable = false)
    private LocalDate startDate;
 
    @NotNull(message = "End date is required")
    @FutureOrPresent(message = "End date must be today or in the future")
    @Column(nullable = false)
    private LocalDate endDate;
 
    @NotNull(message = "Budget is required")
    @Positive(message = "Budget must be a positive value")
    @Column(nullable = false)
    private Double budget;
 
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Program status is required")
    @Column(nullable = false)
    private Status status = Status.PENDING;
 
    // METRICS FIELDS (Required for Reporting/Dashboard)
    private Integer participants = 0;
    private Double successRate = 0.0;
    private String category;
 
}