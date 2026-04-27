package com.cultureconnect.ComplianceAudit.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="audit_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;
 
    private String action;
 
    private String resource;
 
    private LocalDateTime timestamp;
 
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
