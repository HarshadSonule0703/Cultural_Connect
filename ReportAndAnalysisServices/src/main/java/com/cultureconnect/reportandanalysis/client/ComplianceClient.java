package com.cultureconnect.reportandanalysis.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// 1. CHANGE THIS IMPORT
import com.cultureconnect.reportandanalysis.dto.ComplianceDTO; 

@FeignClient(name = "COMPLIANCE-SERVICE")
public interface ComplianceClient {
    
    @GetMapping("/api/compliance/list")
    List<ComplianceDTO> getAllComplianceRecords(); // 2. CHANGE THIS RETURN TYPE
}