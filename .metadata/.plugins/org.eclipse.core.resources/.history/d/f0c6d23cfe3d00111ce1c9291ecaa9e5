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
import com.cultureconnect.programgrant.dto.CulturalProgramResponseDto;
import com.cultureconnect.programgrant.feign.ComplianceClient;
import com.cultureconnect.programgrant.service.CulturalProgramService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST Controller for managing Cultural Program resources.
 * Acts as the entry point for all API requests related to program administration,
 * providing endpoints for full CRUD operations.
 */
@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class CulturalProgramController {

    // Initialize Logger for tracking incoming HTTP requests and controller-level events
    private static final Logger logger = LoggerFactory.getLogger(CulturalProgramController.class);

    private final CulturalProgramService programService;

    private final ComplianceClient complianceClient;

    /**
     * Endpoint to create a new cultural program.
     */
    @PostMapping("/createProgram")
    public ResponseEntity<CulturalProgramResponseDto> createProgram(@Valid @RequestBody CulturalProgramRequestDto requestDto) {
        logger.info("REST Request: Create new Cultural Program with title: {}", requestDto.getTitle());
        
        CulturalProgramResponseDto createdProgram = programService.createProgram(requestDto);
        ResponseEntity<CulturalProgramResponseDto> user = complianceClient.addNewProgram(createdProgram);
        logger.info("REST Response: Program successfully created with ID: {}", createdProgram.getProgramId());
        return new ResponseEntity<>(createdProgram, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve a complete list of all cultural programs.
     * URL: GET http://localhost:1234/api/programs/getAllProgram
     * * @return ResponseEntity containing a list of all programs and HTTP 200 Status.
     */
    @GetMapping("/getAllProgram")
    public ResponseEntity<List<CulturalProgramResponseDto>> getAllPrograms() {
        logger.info("REST Request: Fetching all cultural programs.");
        
        List<CulturalProgramResponseDto> programs = programService.getAllPrograms();
        
        logger.info("REST Response: Returning {} programs.", programs.size());
        return ResponseEntity.ok(programs);
    }

    /**
     * Endpoint to retrieve details of a specific program by ID.
     * URL: GET http://localhost:1234/api/programs/getProgram/{id}
     * * @param id The unique identifier of the program.
     * @return ResponseEntity containing the program details.
     */
    @GetMapping("/getProgram/{id}")
    public ResponseEntity<CulturalProgramResponseDto> getProgramById(@PathVariable Long id) {
        logger.info("REST Request: Fetching Program details for ID: {}", id);
        
        CulturalProgramResponseDto program = programService.getProgramById(id);
        
        return ResponseEntity.ok(program);
    }

    /**
     * Endpoint to update an existing program's attributes (Budget, Dates, Status, etc.).
     * URL: PUT http://localhost:1234/api/programs/updateProgram/{id}
     */
    @PutMapping("/updateProgram/{id}")
    public ResponseEntity<CulturalProgramResponseDto> updateProgram(
            @PathVariable Long id, 
            @Valid @RequestBody CulturalProgramRequestDto requestDto) {
        
        logger.info("REST Request: Updating Program ID: {} with new title: {}", id, requestDto.getTitle());
        
        CulturalProgramResponseDto updatedProgram = programService.updateProgram(id, requestDto);
        
        logger.info("REST Response: Program ID: {} updated successfully.", id);
        return ResponseEntity.ok(updatedProgram);
    }

    /**
     * Endpoint to remove a program from the system.
     * URL: DELETE http://localhost:1234/api/programs/deleteProgram/{id}
     * * @param id The ID of the program to delete.
     * @return ResponseEntity with HTTP 204 No Content status on success.
     */
    @DeleteMapping("/deleteProgram/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id) {
        logger.warn("REST Request: Deleting Program ID: {}", id);
        
        programService.deleteProgram(id);
        
        logger.info("REST Response: Program ID: {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }
}