package com.cultureconnect.programgrant.serviceimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultureconnect.programgrant.client.NotificationClient;
import com.cultureconnect.programgrant.dto.CreateNotificationRequest;
import com.cultureconnect.programgrant.dto.GrantApplicationRequestDto;
import com.cultureconnect.programgrant.dto.GrantApplicationResponseDto;
import com.cultureconnect.programgrant.dto.GrantApprovalDto;
import com.cultureconnect.programgrant.entity.CulturalProgram;
import com.cultureconnect.programgrant.entity.GrantApplication;
import com.cultureconnect.programgrant.enums.NotificationCategory; // Ensure this is imported
import com.cultureconnect.programgrant.enums.Status;
import com.cultureconnect.programgrant.exception.ResourceNotFoundException;
import com.cultureconnect.programgrant.feign.CitizenClient;
import com.cultureconnect.programgrant.repository.CulturalProgramRepository;
import com.cultureconnect.programgrant.repository.GrantApplicationRepository;
import com.cultureconnect.programgrant.service.GrantApplicationService;
import com.cultureconnect.programgrant.service.GrantService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrantApplicationServiceImpl implements GrantApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(GrantApplicationServiceImpl.class);

    private final CitizenClient citizenClient;
    private final GrantApplicationRepository applicationRepository;
    private final CulturalProgramRepository programRepository;
    private final GrantService grantService;
    private final NotificationClient notificationClient;

    @Override
    @Transactional
    public GrantApplicationResponseDto submitApplication(GrantApplicationRequestDto dto) {
        var citizen = citizenClient.getCitizenById(dto.getCitizenId());

        CulturalProgram program = programRepository.findById(dto.getProgramId())
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with ID: " + dto.getProgramId()));

        GrantApplication application = new GrantApplication();
        application.setCitizenId(dto.getCitizenId());
        application.setProgramId(dto.getProgramId());
        application.setStatus(Status.PENDING);
        applicationRepository.save(application);

        // ✅ FIXED: Use the centralized notification helper
        sendNotificationHelper(
            application.getCitizenId(),
            citizen.getEmail(),
            application.getApplicationId(),
            NotificationCategory.GRANT, // ✅ Pass Enum, not String
            "✅ Your grant application for program '" + program.getName() + "' has been submitted successfully."
        );

        return mapToResponseDto(application, program.getName());
    }

    @Override
    @Transactional
    public GrantApplicationResponseDto updateApplicationStatus(Long id, GrantApprovalDto approvalDto) {
        GrantApplication app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + id));

        if (app.getStatus() == Status.APPROVED) {
            throw new IllegalStateException("Application is already approved");
        }

        if (approvalDto.getStatus() == Status.APPROVED) {
            if (approvalDto.getApprovedAmount() == null || approvalDto.getApprovedAmount() <= 0) {
                throw new IllegalArgumentException("Approved amount must be positive");
            }
            grantService.disburseGrant(app, approvalDto.getApprovedAmount());
        }

        app.setStatus(approvalDto.getStatus());
        applicationRepository.save(app);

        var citizen = citizenClient.getCitizenById(app.getCitizenId());

        // ✅ FIXED: Using Enum and helper
        String message = (approvalDto.getStatus() == Status.APPROVED)
                ? "🎉 Congratulations! Your grant application has been APPROVED."
                : "❌ We regret to inform you that your grant application has been REJECTED.";

        sendNotificationHelper(
            app.getCitizenId(),
            citizen.getEmail(),
            app.getApplicationId(),
            NotificationCategory.GRANT,
            message
        );

        return mapToResponseDto(app);
    }

    // ======================================================
    // ✅ HELPER METHOD TO AVOID REPEATING ERRORS
    // ======================================================
    private void sendNotificationHelper(Long userId, String email, Long entityId, NotificationCategory category, String message) {
        try {
            CreateNotificationRequest request = new CreateNotificationRequest();
            request.setUserId(userId);
            request.setEmail(email); // Use setMail or setEmail based on your DTO field name
            request.setEntityId(entityId);
            request.setCategory(category); // ✅ Set as Enum
            request.setMessage(message);

            // ✅ Call sendNotification (The main endpoint we set up earlier)
            notificationClient.sendNotification(request);
            logger.info("Notification successfully queued for Citizen: {}", userId);
        } catch (Exception ex) {
            logger.warn("Notification failed for user {}: {}", userId, ex.getMessage());
        }
    }

    // ... Other methods (getAll, getById, etc.) remain unchanged ...
    
    @Override
    @Transactional(readOnly = true)
    public List<GrantApplicationResponseDto> getApplicationsByCitizen(Long citizenId) {
        return applicationRepository.findByCitizenId(citizenId).stream().map(this::mapToResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GrantApplicationResponseDto> getAllApplications() {
        return applicationRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public GrantApplicationResponseDto getApplicationById(Long id) {
        GrantApplication app = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + id));
        return mapToResponseDto(app);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GrantApplicationResponseDto> getApplicationsByStatus(Status status) {
        return applicationRepository.findByStatus(status).stream().map(this::mapToResponseDto).toList();
    }

    private GrantApplicationResponseDto mapToResponseDto(GrantApplication app) {
        CulturalProgram program = programRepository.findById(app.getProgramId())
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with ID: " + app.getProgramId()));
        return mapToResponseDto(app, program.getName());
    }

    private GrantApplicationResponseDto mapToResponseDto(GrantApplication app, String programName) {
        return new GrantApplicationResponseDto(
                app.getApplicationId(),
                app.getCitizenId(),
                app.getProgramId(),
                programName,
                app.getSubmittedDate(),
                app.getStatus()
        );
    }
}