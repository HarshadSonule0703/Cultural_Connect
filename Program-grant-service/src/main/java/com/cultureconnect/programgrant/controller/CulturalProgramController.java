package com.cultureconnect.programgrant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureconnect.programgrant.dto.CulturalProgramRequestDto;
import com.cultureconnect.programgrant.dto.CulturalProgramResponseCitizenDto;
import com.cultureconnect.programgrant.dto.CulturalProgramResponseDto;
import com.cultureconnect.programgrant.feign.ComplianceClient;
import com.cultureconnect.programgrant.service.CulturalProgramService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class CulturalProgramController {

    private static final Logger logger = LoggerFactory.getLogger(CulturalProgramController.class);

    private final CulturalProgramService programService;
    private final ComplianceClient complianceClient;

    @PostMapping("/createProgram")
    public ResponseEntity<CulturalProgramResponseDto> createProgram(
            @Valid @RequestBody CulturalProgramRequestDto requestDto) {

        logger.info("REST Request: Create new Cultural Program with title: {}", requestDto.getTitle());
        CulturalProgramResponseDto createdProgram = programService.createProgram(requestDto);

        try {
            complianceClient.addNewProgram(createdProgram);
        } catch (Exception ex) {
            logger.warn("Compliance service unavailable.");
        }

        return new ResponseEntity<>(createdProgram, HttpStatus.CREATED);
    }

    @PutMapping("/updateProgram/{id}")
    public ResponseEntity<CulturalProgramResponseDto> updateProgram(
            @PathVariable Long id, 
            @Valid @RequestBody CulturalProgramRequestDto requestDto) {
        
        logger.info("REST Request: Updating Program ID: {}", id);
        CulturalProgramResponseDto updatedProgram = programService.updateProgram(id, requestDto);
        
        return ResponseEntity.ok(updatedProgram);
    }

    @DeleteMapping("/deleteProgram/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        logger.warn("REST Request: Deleting Program ID: {}", id);
        programService.deleteProgram(id);
        
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllProgram")
    public ResponseEntity<List<CulturalProgramResponseDto>> getAllPrograms() {
        return ResponseEntity.ok(programService.getAllPrograms());
    }

    @GetMapping("/getAllProgramForCitizen")
    public ResponseEntity<List<CulturalProgramResponseCitizenDto>> getAllProgramsForCitizen() {
        return ResponseEntity.ok(programService.getAllProgramsForCitizen());
    }

    @GetMapping("/getProgram/{id}")
    public ResponseEntity<CulturalProgramResponseDto> getProgramById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgramById(id));
    }
}