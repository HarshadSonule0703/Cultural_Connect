package com.cultureconnect.ComplianceAudit.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.cultureconnect.ComplianceAudit.enums.Status;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long auditId;

    private String scope;
    private String findings;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long officerId; 
}