package com.cultureconnect.ComplianceAudit.entity;

import com.cultureconnect.ComplianceAudit.enums.Status;
import com.cultureconnect.ComplianceAudit.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
 
    @NotBlank(message="Name cannot be empty")
    private String name;
 
    @Enumerated(EnumType.STRING)
    private UserRole role;
 
    @Email(message="Invalid email")
    @NotBlank
    @Column(unique=true)
    private String email;
 
    @Size(min=10,max=10,message="Phone must be 10 digits")
    private String phone;
 
    @Enumerated(EnumType.STRING)
    private Status status;
 
}

/*
 * Relationships:
One User → Many AuditLogs
One User → Many Notifications
One User → Many PreservationActivities
One User → Many Audits*/
