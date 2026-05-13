package com.cultureconnect.reportandanalysis.serviceimpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultureconnect.reportandanalysis.client.ComplianceClient;
import com.cultureconnect.reportandanalysis.client.EventClient;
import com.cultureconnect.reportandanalysis.client.GrantClient;
import com.cultureconnect.reportandanalysis.client.ProgramClient;
import com.cultureconnect.reportandanalysis.dto.ComplianceDTO;
import com.cultureconnect.reportandanalysis.dto.DashboardSummaryDTO;
import com.cultureconnect.reportandanalysis.dto.EventDTO;
import com.cultureconnect.reportandanalysis.dto.GrantDTO;
import com.cultureconnect.reportandanalysis.dto.ProgramDTO;
import com.cultureconnect.reportandanalysis.dto.ReportDTO;
import com.cultureconnect.reportandanalysis.entity.Report;
import com.cultureconnect.reportandanalysis.exception.ResourceNotFoundException;
import com.cultureconnect.reportandanalysis.repository.ReportRepository;
import com.cultureconnect.reportandanalysis.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private static final String METRIC_ITEMS_KEY = "items";
    private static final String METRIC_COUNT_KEY = "count";

    // 1. Injected Feign Clients (Network Calls)
    private final ProgramClient programClient;
    private final GrantClient grantClient;
    private final EventClient eventClient;
    private final ComplianceClient complianceClient;

    // 2. Local Repository & Tools
    private final ReportRepository reportRepo;
    private final ObjectMapper objectMapper;

    @Override
    public DashboardSummaryDTO getDashboardSummary() {
        log.info("Fetching dashboard statistics via OpenFeign...");
        try {
            List<ProgramDTO> programs = programClient.getAllPrograms();
            
            // ✅ CALL THE NEW METHOD
            List<GrantDTO> grants = grantClient.getAllApplicationsForReport(); 
            
            List<EventDTO> events = eventClient.getAllEvents();

            return new DashboardSummaryDTO(
                (long) programs.size(),      
                (long) grants.size(),        
                (long) events.size(),        
                calculateComplianceRate()
            );
        } catch (Exception e) {
            log.error("Failed to fetch dashboard summary", e);
            throw new RuntimeException("Upstream service unavailable.");
        }
    }

    private double calculateComplianceRate() {
        // Fetch the list of compliance records
        List<ComplianceDTO> records = complianceClient.getAllComplianceRecords();
        
        long total = records.size();
        log.info("Total compliance records fetched: {}", total); // 👈 SEE HOW MANY IT FOUND
        
        if (total == 0) return 0.0;

        // 👈 PRINT OUT EVERY SINGLE RECORD TO SEE WHAT IS WRONG
        for (ComplianceDTO record : records) {
            log.info("Record ID: {}, Status: {}", record.getId(), record.getStatus());
        }

        // Count how many are 'VERIFIED' locally!
        long successCount = records.stream()
            .filter(c -> c.getStatus() != null && "VERIFIED".equalsIgnoreCase(c.getStatus().trim()))
            .count();
            
        log.info("Total VERIFIED records found: {}", successCount); // 👈 SEE THE COUNT
            
        return (successCount * 100.0) / total;
    }

    @Override
    @Transactional
    public void deleteReport(Long id) {
        log.info("Attempting to delete report with ID: {}", id);
        
        // Find the report or throw your existing exception if it doesn't exist
        Report report = reportRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete: No report found with ID: " + id));
        
        reportRepo.delete(report);
        log.info("Successfully deleted report with ID: {}", id);
    }


    @Override
    @Transactional
    public ReportDTO generateReport(Report.ReportScope scope) {
        log.info("Generating a new report via OpenFeign for scope: {}", scope);

        if (scope == null) {
            throw new IllegalArgumentException("Report scope is mandatory");
        }

        try {
            Map<String, Object> metricsMap = new HashMap<>();
            metricsMap.put("generated_at", LocalDateTime.now().toString());

            // Route to the specific Feign Client based on the requested scope
            switch (scope) {
                case PROGRAM: addProgramMetrics(metricsMap); break;
                case GRANT:   addGrantMetrics(metricsMap);   break;
                case EVENT:   addEventMetrics(metricsMap);   break;
                default:      throw new IllegalArgumentException("Invalid scope type");
            }

            // Serialize the pulled data and save it locally
            String jsonMetrics = objectMapper.writeValueAsString(metricsMap);

            Report report = new Report();
            report.setScope(scope);
            report.setMetrics(jsonMetrics);

            Report savedReport = reportRepo.save(report);
            return ReportDTO.fromEntity(savedReport);

        } catch (Exception e) {
            log.error("Error occurred while generating report JSON via Feign: {}", e.getMessage());
            throw new ResourceNotFoundException("Report generation failed. Upstream service may be down.");
        }
    }

 // --- HELPER METHODS FOR FEIGN CALLS ---

    private void addProgramMetrics(Map<String, Object> metrics) {
        List<ProgramDTO> programs = programClient.getAllPrograms();
        metrics.put(METRIC_COUNT_KEY, programs.size());
        
        metrics.put(METRIC_ITEMS_KEY, programs.stream().map(p -> {
            Map<String, Object> m = new HashMap<>();
            m.put("ID", p.getId());
            m.put("Program Name", p.getName() != null ? p.getName() : "Unknown");
            m.put("Status", p.getStatus() != null ? p.getStatus() : "N/A");
            m.put("Budget", p.getBudget() != null ? p.getBudget() : 0.0);
            m.put("Start Date", p.getStartDate() != null ? p.getStartDate() : "N/A");
            m.put("End Date", p.getEndDate() != null ? p.getEndDate() : "N/A");
            return m;
        }).collect(Collectors.toList()));
    }

    private void addGrantMetrics(Map<String, Object> metrics) {
        // ✅ CALL THE NEW METHOD
        List<GrantDTO> grants = grantClient.getAllApplicationsForReport(); 
        metrics.put(METRIC_COUNT_KEY, grants.size()); 
        
        metrics.put(METRIC_ITEMS_KEY, grants.stream().map(g -> {
            Map<String, Object> m = new HashMap<>();
            m.put("App ID", g.getApplicationId()); // Using Application ID
            m.put("Applicant", g.getCitizenName() != null ? g.getCitizenName() : "Unknown");
            m.put("Program", g.getProgramName() != null ? g.getProgramName() : "Unknown");
            m.put("Status", g.getStatus() != null ? g.getStatus() : "Pending");
            m.put("Grant Amount", g.getGrantAmount() != null ? "$" + g.getGrantAmount() : "Not Disbursed");
            m.put("Date Submitted", g.getSubmittedDate() != null ? g.getSubmittedDate() : "N/A");
            return m;
        }).collect(Collectors.toList()));
    }

    private void addEventMetrics(Map<String, Object> metrics) {
        List<EventDTO> events = eventClient.getAllEvents();
        metrics.put(METRIC_COUNT_KEY, events.size()); 
        
        metrics.put(METRIC_ITEMS_KEY, events.stream().map(e -> {
            Map<String, Object> m = new HashMap<>();
            m.put("Event ID", e.getId());
            m.put("Event Name", e.getName() != null ? e.getName() : "Unnamed");
            m.put("Location", e.getLocation() != null ? e.getLocation() : "TBD");
            m.put("Date", e.getDate() != null ? e.getDate() : "TBD");
            m.put("Status", e.getStatus() != null ? e.getStatus() : "N/A");
            return m;
        }).collect(Collectors.toList()));
    }

    // --- STANDARD LOCAL DB FETCHES ---

    @Override
    @Transactional(readOnly = true)
    public List<ReportDTO> getAllReports() {
        return reportRepo.findAll().stream()
                .map(ReportDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ReportDTO getReportById(Long id) {
        return reportRepo.findById(id)
                .map(ReportDTO::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("No report found with ID: " + id));
    }
}