package com.cultureconnect.notification.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultureconnect.notification.client.UserClient;
import com.cultureconnect.notification.dto.CreateNotificationRequest;
import com.cultureconnect.notification.dto.CreatelNotificationRequest;
import com.cultureconnect.notification.dto.NotificationDTO;
import com.cultureconnect.notification.dto.UserDTO;
import com.cultureconnect.notification.entity.Notification;
import com.cultureconnect.notification.enums.NotificationCategory;
import com.cultureconnect.notification.enums.NotificationStatus;
import com.cultureconnect.notification.exception.BadRequestException;
import com.cultureconnect.notification.exception.NotFoundException;
import com.cultureconnect.notification.repository.NotificationRepository;
import com.cultureconnect.notification.service.EmailService;
import com.cultureconnect.notification.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final UserClient userClient;

    public NotificationServiceImpl(NotificationRepository notificationRepository, EmailService emailService,
            UserClient userClient) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
        this.userClient = userClient;
    }

    // ================= SEND NOTIFICATION (STANDARD FLOW) =================
    @Override
    @Transactional
    public NotificationDTO sendNotification(CreateNotificationRequest request) {
        log.info("Processing standard notification for user: {}", request.getUserId());

        // 1. Attempt to fetch user details (Fault Tolerant)
        UserDTO user = null;
        try {
            user = userClient.getUserById(request.getUserId());
        } catch (Exception ex) {
            // ✅ CRITICAL FIX: We log the error but do NOT throw it. 
            // This prevents the transaction from rolling back if the user isn't in Auth-Service yet.
            log.warn("Auth Service could not find user {}. Notification will be saved to DB, but email will be skipped.", request.getUserId());
        }
        
        // 2. Determine message based on category template
        String finalMessage = determineMessage(request.getCategory(), request.getMessage());

        // 3. Save to DB (This now happens even if the UserClient call failed)
        Notification saved = saveInternal(request.getUserId(), request.getEntityId(), 
                request.getCategory(), finalMessage);

        // 4. Dispatch Email only if user details were successfully fetched
        if (user != null && user.getEmail() != null) {
            try {
                emailService.sendSimpleEmail(user.getEmail(), "CultureConnect | " + request.getCategory(), 
                        finalMessage + "\n\nRegards,\nCultureConnect Team");
            } catch (Exception e) {
                log.error("Database record saved, but SMTP email dispatch failed: {}", e.getMessage());
            }
        }

        return mapToDTO(saved);
    }

    // ================= UNIVERSAL NOTIFICATION =================
    @Override
    @Transactional
    public void sendUniversalNotification(CreatelNotificationRequest request) {
        log.info("Processing universal notification for email: {}", request.getEmail());

        if (request.getUserId() == null || request.getEmail() == null || request.getMessage() == null) {
            throw new BadRequestException("UserId, Email, and Message are all required.");
        }

        saveInternal(request.getUserId(), request.getEntityId(), request.getCategory(), request.getMessage());
        emailService.sendSimpleEmail(request.getEmail(), "CultureConnect Notification", request.getMessage());
    }

    // ================= READ / DELETE OPERATIONS =================

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notification not found with ID: " + id));

        notification.setStatus(NotificationStatus.READ);
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new NotFoundException("Notification not found with ID: " + id);
        }
        notificationRepository.deleteById(id);
    }

    // ================= PRIVATE HELPER LOGIC =================

    private String determineMessage(NotificationCategory category, String manualMessage) {
        if (manualMessage != null && !manualMessage.isBlank()) {
            return manualMessage;
        }

        return switch (category) {
            case PROJECT -> "New project-related activity at CultureConnect.";
            case COMPLIANCE -> "A compliance check has been updated.";
            case GRANT -> "An update is available for your grant application.";
            case PROGRAM -> "Information about a cultural program has changed.";
            case GENERAL -> throw new BadRequestException("Message is required when no category template exists.");
            default -> "New notification from CultureConnect.";
        };
    }

    private Notification saveInternal(Long userId, Long entityId, NotificationCategory category, String message) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setEntityId(entityId);
        n.setCategory(category != null ? category : NotificationCategory.GENERAL);
        n.setMessage(message);
        n.setStatus(NotificationStatus.SENT);
        n.setCreatedDate(LocalDateTime.now());
        return notificationRepository.save(n);
    }

    private NotificationDTO mapToDTO(Notification n) {
        NotificationDTO dto = new NotificationDTO();
        dto.setNotificationId(n.getId());
        dto.setUserId(n.getUserId());
        dto.setEntityId(n.getEntityId());
        dto.setMessage(n.getMessage());
        dto.setCategory(n.getCategory());
        dto.setStatus(n.getStatus().name());
        dto.setCreatedDate(n.getCreatedDate());
        return dto;
    }
}