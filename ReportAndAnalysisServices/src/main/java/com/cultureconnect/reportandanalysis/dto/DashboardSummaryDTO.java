package com.cultureconnect.reportandanalysis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDTO {
    private long totalPrograms;
    private long totalGrants;
    private long totalEvents;
    private double complianceRate;
}