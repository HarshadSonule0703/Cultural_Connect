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

import com.cultureconnect.programgrant.client.NotificationClient;
import com.cultureconnect.programgrant.dto.CreateNotificationRequest;
import com.cultureconnect.programgrant.dto.GrantApplicationRequestDto;
import com.cultureconnect.programgrant.dto.GrantApplicationResponseDto;
import com.cultureconnect.programgrant.dto.GrantApprovalDto;
import com.cultureconnect.programgrant.enums.NotificationCategory;
import com.cultureconnect.programgrant.enums.Status;
import com.cultureconnect.programgrant.repository.GrantApplicationRepository;
import com.cultureconnect.programgrant.service.GrantApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class GrantApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(GrantApplicationController.class);

    private final GrantApplicationRepository applicationRepository;
    private final GrantApplicationService applicationService;
    private final NotificationClient notificationClient; // ✅ Inject Client

//    @PostMapping("/submitApplication")
//    public ResponseEntity<GrantApplicationResponseDto> submitApplication(
//            @Valid @RequestBody GrantApplicationRequestDto requestDto) {
//        
//        logger.info("REST Request: New Application for Citizen ID: {}", requestDto.getCitizenId());
//        GrantApplicationResponseDto response = applicationService.submitApplication(requestDto);
//        
//        // 🔵 TRIGGER: Notify the Citizen that application is received
//        triggerNotification(
//            response.getCitizenId(), 
//            response.getApplicationId(), 
//            NotificationCategory.GRANT, 
//            "Your grant application has been submitted successfully! Application ID: " + response.getApplicationId()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
    @PostMapping("/submitApplication")
    public ResponseEntity<?> submitApplication(
            @Valid @RequestBody GrantApplicationRequestDto requestDto) {

        try {
            GrantApplicationResponseDto response =
                    applicationService.submitApplication(requestDto);

//            triggerNotification(
//                response.getCitizenId(),
//                response.getApplicationId(),
//                NotificationCategory.GRANT,
//                "Your grant application has been submitted successfully! Application ID: " 
//                + response.getApplicationId()
//            );

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalStateException ex) {

            // ✅ RETURN CLEAN 400 RESPONSE
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Already applied for this program");

        } catch (Exception ex) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong");

        }
    }


    @PatchMapping("/status/{id}")
    public ResponseEntity<GrantApplicationResponseDto> updateStatus(
            @PathVariable Long id, 
            @Valid @RequestBody GrantApprovalDto approvalDto) {
        
        logger.info("REST Request: Updating Application ID: {} to {}", id, approvalDto.getStatus());
        GrantApplicationResponseDto response = applicationService.updateApplicationStatus(id, approvalDto);
        
        // 🔵 TRIGGER: Notify the Citizen of Approval or Rejection
        String customMessage = (response.getStatus() == Status.APPROVED) ? 
            "Congratulations! Your grant application ID " + id + " has been APPROVED." : 
            "Status Update: Your grant application ID " + id + " is now " + response.getStatus();

        triggerNotification(
            response.getCitizenId(), 
            id, 
            NotificationCategory.GRANT, 
            customMessage
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllApplicaiton")
    public ResponseEntity<List<GrantApplicationResponseDto>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }
    
    @GetMapping("/getAllApplicaitonForReport")
    public ResponseEntity<List<GrantApplicationResponseDto>> getAllApplicationsForReport() {
        return ResponseEntity.ok(applicationService.getAllApplicationsForReport());
    }

    @GetMapping("/citizen/{citizenId}")
    public ResponseEntity<List<GrantApplicationResponseDto>> getByCitizen(@PathVariable Long citizenId) {
        return ResponseEntity.ok(applicationService.getApplicationsByCitizen(citizenId));
    }

    @GetMapping("/getApplicatoinById/{applicationId}")
    public ResponseEntity<GrantApplicationResponseDto> getApplicationById(@PathVariable Long applicationId) {
        return ResponseEntity.ok(applicationService.getApplicationById(applicationId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<GrantApplicationResponseDto>> getByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(applicationService.getApplicationsByStatus(status));
    }
    @GetMapping("/check/{citizenId}/{programId}")
    public Boolean checkApplication(
            @PathVariable Long citizenId,
            @PathVariable Long programId) {

        return applicationRepository
            .existsByCitizenIdAndProgramId(citizenId, programId);
    }

    // Helper Trigger Method
    private void triggerNotification(Long userId, Long entityId, NotificationCategory category, String message) {
        try {
            CreateNotificationRequest note = new CreateNotificationRequest();
            note.setUserId(userId);
            note.setEntityId(entityId);
            note.setCategory(category);
            note.setMessage(message);
            notificationClient.sendNotification(note);
        } catch (Exception e) {
            logger.error("Grant Notification failed: {}", e.getMessage());
        }
    }
}