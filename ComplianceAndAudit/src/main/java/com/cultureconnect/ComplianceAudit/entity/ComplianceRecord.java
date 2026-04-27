package com.cultureconnect.ComplianceAudit.entity;

import java.time.LocalDate;

import com.cultureconnect.ComplianceAudit.enums.ComplianceType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="compliance_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRecord {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complianceId;
 
    private Long entityId;
 
    private ComplianceType type;
 
    private String result;
 
    private LocalDate date;
 
    private String notes;
}



