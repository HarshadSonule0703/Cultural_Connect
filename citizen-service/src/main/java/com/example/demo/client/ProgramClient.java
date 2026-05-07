package com.example.demo.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.CulturalProgramResponseCitizenDto;
 
 
@FeignClient(name="program-grant-service")
public interface ProgramClient {
	@GetMapping("/api/programs/getAllProgramForCitizen")
    public ResponseEntity<List<CulturalProgramResponseCitizenDto>> getAllProgramsForCitizen();
}