package com.cultureconnect.ComplianceAudit.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cultureconnect.ComplianceAudit.dto.ComplianceResponseDTO;

import java.util.List;

@FeignClient(name = "compliance-client", url = "http://localhost:1241")
public interface ComplianceClient {
    @GetMapping("/compliance/list")
    List<ComplianceResponseDTO> getAllComplianceRecords();

    @GetMapping("/compliance/{id}")
    ComplianceResponseDTO getComplianceById(@PathVariable("id") Long id);
}