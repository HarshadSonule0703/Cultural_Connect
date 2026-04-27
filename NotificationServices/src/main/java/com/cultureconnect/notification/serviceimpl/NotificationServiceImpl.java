package com.cultureconnect.notification.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultureconnect.notification.client.UserClient;
import com.cultureconnect.notification.dto.NotificationDTO;
import com.cultureconnect.notification.dto.UserReqDTO;
import com.cultureconnect.notification.entity.Notification;
import com.cultureconnect.notification.enums.NotificationStatus;
import com.cultureconnect.notification.repository.NotificationRepository;
import com.cultureconnect.notification.service.EmailService;
import com.cultureconnect.notification.service.NotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepo;
    private final EmailService emailService;
    
 // INJECT FEIGN CLIENT INSTEAD OF REPOSITORY
    private final UserClient userClient;
    

    @Override
    @Transactional // Ensures DB integrity if email or save fails
    public NotificationDTO sendNotification(NotificationDTO dto) {
        log.info("Service: Starting notification process for User: {}", dto.getUserId());

     // 1. Fetch User via OpenFeign (Network Call)
        UserReqDTO user;
        try {
            user = userClient.getUserById(dto.getUserId());
        } catch (Exception e) {
            log.error("Failed to fetch user {} from User Service", dto.getUserId(), e);
            throw new RuntimeException("User not found or User Service is down");
        }
        
        // 2. Map DTO to Entity and set default PENDING status
        Notification notification = new Notification();
        notification.setUserId(dto.getUserId());
        notification.setEntityId(dto.getEntityId());
        notification.setCategory(dto.getCategory());
        notification.setMessage(dto.getMessage());
        notification.setStatus(NotificationStatus.PENDING); 

        // 3. Save initial record
        Notification saved = notificationRepo.save(notification);

        // 4. Attempt Email Dispatch
        try {
            emailService.sendSimpleEmail(
                user.getEmail(), // Pulled from the Feign response
                "CultureConnect | " + dto.getCategory(), 
                dto.getMessage()
            );
            
            // 5. If email succeeds, upgrade status to SENT
            saved.setStatus(NotificationStatus.SENT);
            notificationRepo.save(saved);
            log.info("Notification {} successfully dispatched via Email", saved.getNotificationId());
        } catch (Exception e) {
            // Logic: Notification is still saved in DB as PENDING for internal app view
            log.error("Email delivery failed for user {}: {}", user.getEmail(), e.getMessage());
        }

        return NotificationDTO.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getNotificationsForUser(Long userId) {
        log.debug("Service: Fetching notification history for user {}", userId);
        return notificationRepo.findByUserIdOrderByCreatedDateDesc(userId).stream()
                .map(NotificationDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Implementation for fetching only unread (PENDING/SENT) notifications
     */
    @Override
    @Transactional(readOnly = true)
    public List<NotificationDTO> getUnreadNotificationsForUser(Long userId) {
        log.info("Service: Fetching unread notifications for user {}", userId);
        
        // FIX: We now fetch anything that is NOT 'READ'
        return notificationRepo.findByUserIdAndStatusNot(userId, NotificationStatus.READ).stream()
                .map(NotificationDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepo.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found ID: " + notificationId));
        
        notification.setStatus(NotificationStatus.READ);
        notificationRepo.save(notification);
        log.info("Notification {} marked as READ in database", notificationId);
    }

    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        // FIX: Fetch everything that is NOT 'READ' to update them
        List<Notification> unread = notificationRepo.findByUserIdAndStatusNot(userId, NotificationStatus.READ);
        
        if (!unread.isEmpty()) {
            unread.forEach(n -> n.setStatus(NotificationStatus.READ));
            notificationRepo.saveAll(unread);
            log.info("Bulk Update: {} notifications marked as READ for user {}", unread.size(), userId);
        } else {
            log.info("No unread notifications to update for user {}", userId);
        }
    }
}