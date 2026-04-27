package com.cultureconnect.reportandanalysis.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cultureconnect.reportandanalysis.dto.DashboardSummaryDTO;
import com.cultureconnect.reportandanalysis.dto.ReportDTO;
import com.cultureconnect.reportandanalysis.entity.Report;
import com.cultureconnect.reportandanalysis.service.ReportService;
import com.cultureconnect.reportandanalysis.util.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/reports")

@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

  

	/**
     * Fetches a snapshot of total counts for Programs, Grants, and Events.
     */
    @GetMapping("/dashboard/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryDTO>> getDashboardSummary() {
        log.info("REST request to fetch dashboard summary");
        DashboardSummaryDTO data = reportService.getDashboardSummary();
        return ResponseEntity.ok(ApiResponse.success("Dashboard summary fetched", data));
    }

    /**
     * Generates a new JSON-based report based on the provided scope.
     * @param scope Must be PROGRAM, GRANT, or EVENT.
     */
    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<ReportDTO>> generateReport(@RequestParam Report.ReportScope scope) {
        log.info("REST request to generate report for scope: {}", scope);
        ReportDTO report = reportService.generateReport(scope);
        return ResponseEntity.ok(ApiResponse.success("Report generated successfully", report));
    }

    /**
     * Retrieves all historically generated reports.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReportDTO>>> getAllReports() {
        log.info("REST request to fetch all reports");
        List<ReportDTO> reports = reportService.getAllReports();
        return ResponseEntity.ok(ApiResponse.success("All reports fetched", reports));
    }

    /**
     * Retrieves a specific report by its Database ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReportDTO>> getReportById(@PathVariable Long id) {
        log.info("REST request to fetch report with ID: {}", id);
        ReportDTO report = reportService.getReportById(id);
        return ResponseEntity.ok(ApiResponse.success("Report fetched", report));
    }
}