package com.cultureconnect.reportandanalysis.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cultureconnect.reportandanalysis.dto.GrantDTO;

@FeignClient(name = "PROGRAM-GRANT-SERVICE") 
public interface GrantClient {

    // Exact match to their controller (including their typo!)
    @GetMapping("/api/applications/getAllApplicaitonForReport")
    List<GrantDTO> getAllApplications(); 
}