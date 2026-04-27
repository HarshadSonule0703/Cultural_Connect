package com.cultureconnect.programgrant.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureconnect.programgrant.dto.GrantApplicationRequestDto;
import com.cultureconnect.programgrant.dto.GrantApplicationResponseDto;
import com.cultureconnect.programgrant.dto.GrantApprovalDto;
import com.cultureconnect.programgrant.enums.Status;
import com.cultureconnect.programgrant.service.GrantApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Provides endpoints for Citizens to submit applications and for Cultural Officers 
 * to review, filter, and approve/reject them.
 */
@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class GrantApplicationController {

    // Initialize Logger for tracking application processing and officer actions
    private static final Logger logger = LoggerFactory.getLogger(GrantApplicationController.class);

    private final GrantApplicationService applicationService;


    /**
     * Endpoint for Citizens/Artists to apply for a grant.
     * URL: POST http://localhost:1234/api/applications/submitApplication
     * * @param requestDto Validated application data from the citizen.
     * @return ResponseEntity with created application details and HTTP 201 Status.
     */
    @PostMapping("/submitApplication")
    public ResponseEntity<GrantApplicationResponseDto> submitApplication(
            @Valid @RequestBody GrantApplicationRequestDto requestDto) {
        
        logger.info("REST Request: New Grant Application submission for Citizen ID: {}", requestDto.getCitizenId());
        
        GrantApplicationResponseDto response = applicationService.submitApplication(requestDto);
        
        logger.info("REST Response: Application successfully created with ID: {}", response.getApplicationId());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint for Cultural Officers to view all submitted applications.
     * URL: GET http://localhost:1234/api/applications/getAllApplicaiton
     * * @return ResponseEntity containing the full list of applications.
     */
    @GetMapping("/getAllApplicaiton")
    public ResponseEntity<List<GrantApplicationResponseDto>> getAllApplications() {
        logger.info("REST Request: Fetching all grant applications for review.");
        
        List<GrantApplicationResponseDto> applications = applicationService.getAllApplications();
        
        logger.info("REST Response: Returning {} applications.", applications.size());
        return ResponseEntity.ok(applications);
    }

    /**
     * Endpoint for Cultural Officers to update application status (Approve/Reject).
     * URL: PATCH http://localhost:1234/api/applications/status/{id}
     * * @param id The ID of the application to update.
     * @param approvalDto DTO containing the new status and (if approved) the amount.
     * @return ResponseEntity with updated application status.
     */
    @PatchMapping("/status/{id}")
    public ResponseEntity<GrantApplicationResponseDto> updateStatus(
            @PathVariable Long id, 
            @Valid @RequestBody GrantApprovalDto approvalDto) {
        
        logger.info("REST Request: Updating status for Application ID: {} to {}", id, approvalDto.getStatus());
        
        GrantApplicationResponseDto response = applicationService.updateApplicationStatus(id, approvalDto);
        
        logger.info("REST Response: Status updated successfully for Application ID: {}", id);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for Citizens to track their own specific applications.
     * URL: GET http://localhost:1234/api/applications/citizen/{citizenId}
     * * @param citizenId The ID of the citizen/artist.
     * @return List of applications associated with the citizen.
     */
    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<List<GrantApplicationResponseDto>> getByCitizen(@PathVariable Long citizenId) {
        logger.info("REST Request: Fetching applications for Citizen ID: {}", citizenId);
        
        return ResponseEntity.ok(applicationService.getApplicationsByCitizen(citizenId));
    }

    /**
     * Fetches a single application by its primary key.
     * URL: GET http://localhost:1234/api/applications/getApplicatoinById/{application_id}
     */
    @GetMapping("/getApplicatoinById/{applicationId}")
    public ResponseEntity<GrantApplicationResponseDto> getApplicationById(@PathVariable Long applicationId) {
        logger.info("REST Request: Fetching details for Application ID: {}", applicationId);
        
        return ResponseEntity.ok(applicationService.getApplicationById(applicationId));
    }

    /**
     * Endpoint to filter applications by their current status (e.g., PENDING, APPROVED, REJECTED).
     * Useful for Officers to manage their workflow (e.g., viewing only PENDING requests).
     * URL: GET http://localhost:1234/api/applications/status/PENDING
     * * @param status The status enum to filter by.
     * @return List of applications matching the status.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<GrantApplicationResponseDto>> getByStatus(@PathVariable Status status) {
        logger.info("REST Request: Filtering applications by status: {}", status);
        
        List<GrantApplicationResponseDto> filteredList = applicationService.getApplicationsByStatus(status);
        
        logger.info("REST Response: Found {} applications with status: {}", filteredList.size(), status);
        return ResponseEntity.ok(filteredList);
    }
}