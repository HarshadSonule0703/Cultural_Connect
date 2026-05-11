package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.NotificationClient;
import com.example.demo.client.ProgramClient;
import com.example.demo.dto.CitizenDTO;
import com.example.demo.dto.CitizenDocumentDTO;
import com.example.demo.dto.CreateNotificationRequest;
import com.example.demo.dto.CulturalProgramResponseCitizenDto;
import com.example.demo.entity.Citizen;
import com.example.demo.entity.CitizenDocument;
import com.example.demo.enums.NotificationCategory;
import com.example.demo.service.CitizenService;

import jakarta.validation.Valid;

/**
 * Controller for Citizen management. 
 * Integrated with Notification triggers for Registration, Updates, and Document uploads.
 */
@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

    private static final Logger logger = LoggerFactory.getLogger(CitizenController.class);

    private final CitizenService service;
    private final ProgramClient programClientService;
    private final NotificationClient notificationClient;

    public CitizenController(CitizenService service, 
                             ProgramClient programClientService, 
                             NotificationClient notificationClient) {
        this.service = service;
        this.programClientService = programClientService;
        this.notificationClient = notificationClient;
    }

    // ================= REGISTER =================
    @PostMapping("/register")
    public Citizen register(@Valid @RequestBody CitizenDTO dto) {
        logger.info("REST Request: Registering citizen: {}", dto.getName());
        Citizen citizen = service.registerCitizen(dto);
        
        // 🔵 Trigger: Welcome Email/Notification
        triggerNotification(
            citizen.getCitizenId(), 
            citizen.getCitizenId(), 
            NotificationCategory.GENERAL, 
            "Welcome to CultureConnect, " + citizen.getName() + "! Your citizen profile has been successfully created."
        );
        
        return citizen;
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public Citizen update(@PathVariable Long id, @RequestBody CitizenDTO dto) {
        logger.info("REST Request: Updating citizen ID: {}", id);
        Citizen updated = service.updateCitizen(id, dto);
        
        // 🔵 Trigger: Security Alert for profile change
        triggerNotification(
            updated.getCitizenId(), 
            updated.getCitizenId(), 
            NotificationCategory.GENERAL, 
            "Security Notice: Your profile information was recently updated."
        );
        
        return updated;
    }

    // ================= DOCUMENTS =================
    @PostMapping("/{id}/documents")
    public CitizenDocument upload(@PathVariable Long id, @Valid @RequestBody CitizenDocumentDTO dto) {
        logger.info("REST Request: Uploading document for citizen ID: {}", id);
        CitizenDocument doc = service.uploadDocument(id, dto);
        
        // 🔵 Trigger: Document confirmation
        triggerNotification(
            doc.getCitizenId(), 
            doc.getDocumentId(), 
            NotificationCategory.GENERAL, 
            "Document Uploaded: Your " + doc.getDocType() + " has been added to your profile."
        );

        return doc;
    }

    // ================= NOTIFICATION HELPER =================

    /**
     * Centralized helper to trigger non-blocking notifications.
     */
    private void triggerNotification(Long userId, Long entityId, NotificationCategory category, String message) {
        try {
            CreateNotificationRequest note = new CreateNotificationRequest();
            note.setUserId(userId);
            note.setEntityId(entityId);
            note.setCategory(category);
            note.setMessage(message);
            
            notificationClient.sendNotification(note);
            logger.info("Notification successfully sent for user ID: {}", userId);
        } catch (Exception e) {
            // Log the error but allow the main process to complete
            logger.error("Notification trigger failed for user {}: {}", userId, e.getMessage());
        }
    }

    // ================= STANDARD ENDPOINTS =================

    @GetMapping("/{id}")
    public Citizen getById(@PathVariable Long id) {
        return service.getCitizenById(id);
    }

    @GetMapping
    public List<Citizen> getAll() {
        return service.getAllCitizens();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteCitizen(id);
        return "Deleted successfully";
    }

    @GetMapping("/{id}/documents")
    public List<CitizenDocument> getDocs(@PathVariable Long id) {
        return service.getDocuments(id);
    }

    @DeleteMapping("/documents/{docId}")
    public String deleteDoc(@PathVariable Long docId) {
        service.deleteDocument(docId);
        return "Document deleted successfully";
    }

    @GetMapping("/getAllCitizenProgram")
    public ResponseEntity<List<CulturalProgramResponseCitizenDto>> getAllCitizenProgram() {
        return programClientService.getAllProgramsForCitizen();
    }
}