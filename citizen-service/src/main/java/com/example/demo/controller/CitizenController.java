package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.example.demo.dto.GrantApplicationRequestDto;
import com.example.demo.dto.GrantApplicationResponseDto;
import com.example.demo.dto.UpdateCitizenStatusDto;
import com.example.demo.entity.Citizen;
import com.example.demo.entity.CitizenDocument;
import com.example.demo.enums.NotificationCategory;
import com.example.demo.service.CitizenService;

import jakarta.validation.Valid;
 
/**
<<<<<<< HEAD
 * Controller for Citizen management. 
 * Integrated with Notification triggers and Admin management features.
 */
=======
* Controller for Citizen management. 
* Integrated with Notification triggers and Admin management features.
*/
>>>>>>> 9707d2cf5eab1e1f359c08750efd6e80e62c087d
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
<<<<<<< HEAD
        
=======
>>>>>>> 9707d2cf5eab1e1f359c08750efd6e80e62c087d
        triggerNotification(
            citizen.getCitizenId(), 
            citizen.getCitizenId(), 
            NotificationCategory.GENERAL, 
            "Welcome to CultureConnect, " + citizen.getName() + "! Your citizen profile has been successfully created."
        );
        return citizen;
    }
<<<<<<< HEAD

=======
 
>>>>>>> 9707d2cf5eab1e1f359c08750efd6e80e62c087d
    // ================= UPDATE & STATUS =================
    @PutMapping("/{id}")
    public Citizen update(@PathVariable Long id, @RequestBody CitizenDTO dto) {
        logger.info("REST Request: Updating citizen ID: {}", id);
        Citizen updated = service.updateCitizen(id, dto);
<<<<<<< HEAD
        
=======
>>>>>>> 9707d2cf5eab1e1f359c08750efd6e80e62c087d
        triggerNotification(
            updated.getCitizenId(), 
            updated.getCitizenId(), 
            NotificationCategory.GENERAL, 
            "Security Notice: Your profile information was recently updated."
        );
        return updated;
    }
<<<<<<< HEAD

=======
 
>>>>>>> 9707d2cf5eab1e1f359c08750efd6e80e62c087d
    @PatchMapping("/{citizenId}/status")
    public ResponseEntity<String> updateCitizenStatus(
            @PathVariable Long citizenId,
            @RequestBody UpdateCitizenStatusDto dto) {
        service.updateStatus(citizenId, dto);
        return ResponseEntity.ok("Citizen Status Updated Successfully");
<<<<<<< HEAD
=======
    }
 
    // ================= RETRIEVAL =================
 
    @GetMapping("/{id}")
    public Citizen getById(@PathVariable Long id) {
        return service.getCitizenById(id);
    }
 
    @GetMapping("/by-email/{email}")
    public Citizen getByEmail(@PathVariable String email) {
        return service.getCitizenByEmail(email);
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
 
    // ================= DOCUMENTS =================
 
    @PostMapping("/{id}/documents")
    public CitizenDocument upload(@PathVariable Long id, @Valid @RequestBody CitizenDocumentDTO dto) {
        logger.info("REST Request: Uploading document for citizen ID: {}", id);
        CitizenDocument doc = service.uploadDocument(id, dto);
        triggerNotification(
            doc.getCitizenId(), 
            doc.getDocumentId(), 
            NotificationCategory.GENERAL, 
            "Document Uploaded: Your " + doc.getDocType() + " has been added to your profile."
        );
 
        return doc;
>>>>>>> 9707d2cf5eab1e1f359c08750efd6e80e62c087d
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
 
    // ================= EXTERNAL FEIGN ENDPOINTS =================
 
    @GetMapping("/getAllCitizenProgram")
    public ResponseEntity<List<CulturalProgramResponseCitizenDto>> getAllCitizenProgram() {
        return programClientService.getAllProgramsForCitizen();
    }
    
    @PostMapping("/applyProgram")
    public ResponseEntity<GrantApplicationResponseDto> applyProgram(
            @RequestBody GrantApplicationRequestDto dto) {

<<<<<<< HEAD
    // ================= RETRIEVAL =================

    @GetMapping("/{id}")
    public Citizen getById(@PathVariable Long id) {
        return service.getCitizenById(id);
    }

    @GetMapping("/by-email/{email}")
    public Citizen getByEmail(@PathVariable String email) {
        return service.getCitizenByEmail(email);
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

    // ================= DOCUMENTS =================

    @PostMapping("/{id}/documents")
    public CitizenDocument upload(@PathVariable Long id, @Valid @RequestBody CitizenDocumentDTO dto) {
        logger.info("REST Request: Uploading document for citizen ID: {}", id);
        CitizenDocument doc = service.uploadDocument(id, dto);
        
        triggerNotification(
            doc.getCitizenId(), 
            doc.getDocumentId(), 
            NotificationCategory.GENERAL, 
            "Document Uploaded: Your " + doc.getDocType() + " has been added to your profile."
        );

        return doc;
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

    // ================= EXTERNAL FEIGN ENDPOINTS =================

    @GetMapping("/getAllCitizenProgram")
    public ResponseEntity<List<CulturalProgramResponseCitizenDto>> getAllCitizenProgram() {
        return programClientService.getAllProgramsForCitizen();
    }

    // ================= NOTIFICATION HELPER =================

=======
        logger.info("Citizen applying for program ID: {}", dto.getProgramId());

        GrantApplicationResponseDto response =
        		programClientService.submitApplication(dto);

        return ResponseEntity.ok(response);
    }
 
    // ================= NOTIFICATION HELPER =================
 
>>>>>>> 9707d2cf5eab1e1f359c08750efd6e80e62c087d
    private void triggerNotification(Long userId, Long entityId, NotificationCategory category, String message) {
        try {
            CreateNotificationRequest note = new CreateNotificationRequest();
            note.setUserId(userId);
            note.setEntityId(entityId);
            note.setCategory(category);
            note.setMessage(message);
<<<<<<< HEAD
            
=======
>>>>>>> 9707d2cf5eab1e1f359c08750efd6e80e62c087d
            notificationClient.sendNotification(note);
            logger.info("Notification successfully sent for user ID: {}", userId);
        } catch (Exception e) {
            logger.error("Notification trigger failed for user {}: {}", userId, e.getMessage());
        }
    }
}