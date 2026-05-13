package com.cultureconnect.ComplianceAudit.client;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cultureconnect.ComplianceAudit.dto.DetailedProgramDto;
import com.cultureconnect.ComplianceAudit.dto.GrantApplicationResponseDto;

@FeignClient(name = "program-grant-service", url = "http://localhost:8082")
public interface ProgramClient {
	@GetMapping("/api/programs/getProgram/{id}")
	DetailedProgramDto getProgramById(@PathVariable("id") Long id);
	
	@GetMapping("/api/applications/getAllApplicaiton")
	ResponseEntity<List<GrantApplicationResponseDto>> getAllApplications();

}