package com.cultureconnect.reportandanalysis.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @NotNull(message = "Report scope is mandatory")
    @Enumerated(EnumType.STRING)
    private ReportScope scope;

    @NotBlank(message = "Metrics data cannot be empty")
    @Column(columnDefinition = "TEXT", nullable = false)
    private String metrics;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime generatedDate;

    public enum ReportScope {
        PROGRAM, GRANT, EVENT
    }
}