package com.cultureconnect.reportandanalysis.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.cultureconnect.reportandanalysis.dto.GrantDTO;

@FeignClient(name = "PROGRAM-GRANT-SERVICE") 
public interface GrantClient {

    // ✅ Points to your dedicated Report API
    @GetMapping("/api/applications/getAllApplicaitonForReport")
    List<GrantDTO> getAllApplicationsForReport(); 
}