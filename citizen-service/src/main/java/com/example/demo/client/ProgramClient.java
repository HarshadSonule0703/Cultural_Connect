package com.example.demo.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.CulturalProgramResponseCitizenDto;
import com.example.demo.dto.GrantApplicationRequestDto;
import com.example.demo.dto.GrantApplicationResponseDto;
 
 
@FeignClient(name="program-grant-service")
public interface ProgramClient {
	@GetMapping("/api/programs/getAllProgramForCitizen")
    public ResponseEntity<List<CulturalProgramResponseCitizenDto>> getAllProgramsForCitizen();
	

@PostMapping("/api/applications/submitApplication")
    GrantApplicationResponseDto submitApplication(
            @RequestBody GrantApplicationRequestDto requestDto
    );

}