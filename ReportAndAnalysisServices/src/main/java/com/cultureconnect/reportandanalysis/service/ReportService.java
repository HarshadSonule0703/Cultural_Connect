package com.cultureconnect.reportandanalysis.service;

import java.util.List;

import com.cultureconnect.reportandanalysis.dto.DashboardSummaryDTO;
import com.cultureconnect.reportandanalysis.dto.ReportDTO;
import com.cultureconnect.reportandanalysis.entity.Report;

public interface ReportService {
    DashboardSummaryDTO getDashboardSummary();
    ReportDTO generateReport(Report.ReportScope scope);
    List<ReportDTO> getAllReports();
    ReportDTO getReportById(Long id);

    void deleteReport(Long id);
}