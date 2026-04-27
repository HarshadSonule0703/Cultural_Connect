package com.cultureconnect.reportandanalysis.dto;

import java.time.LocalDateTime;

import com.cultureconnect.reportandanalysis.entity.Report;

import lombok.Data;

@Data
public class ReportDTO {
    private Long reportId;
    private Report.ReportScope scope;
    private String metrics;
    private LocalDateTime generatedDate;
 
    public static ReportDTO fromEntity(Report report) {
        ReportDTO dto = new ReportDTO();
        dto.setReportId(report.getReportId());
        dto.setScope(report.getScope());
        dto.setMetrics(report.getMetrics());
        dto.setGeneratedDate(report.getGeneratedDate());
        return dto;
    }
}