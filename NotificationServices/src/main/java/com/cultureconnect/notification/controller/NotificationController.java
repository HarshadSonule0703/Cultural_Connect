package com.cultureconnect.notification.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureconnect.notification.dto.NotificationDTO;
import com.cultureconnect.notification.service.NotificationService;
import com.cultureconnect.notification.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Create and Send a new notification
    @PostMapping
    public ResponseEntity<ApiResponse<NotificationDTO>> sendNotification(@Valid @RequestBody NotificationDTO dto) {
        log.info("API Request: Sending notification to user {}", dto.getUserId());
        NotificationDTO sent = notificationService.sendNotification(dto);
        return ResponseEntity.ok(ApiResponse.success("Notification processed successfully", sent));
    }

    // Get full history for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getUserNotifications(@PathVariable Long userId) {
        log.info("API Request: Fetching all notifications for user {}", userId);
        List<NotificationDTO> notifications = notificationService.getNotificationsForUser(userId);
        return ResponseEntity.ok(ApiResponse.success("All notifications fetched", notifications));
    }

    // Get only unread (Pending/Sent) notifications
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<ApiResponse<List<NotificationDTO>>> getUnreadNotifications(@PathVariable Long userId) {
        log.info("API Request: Fetching unread notifications for user {}", userId);
        List<NotificationDTO> unread = notificationService.getUnreadNotificationsForUser(userId);
        return ResponseEntity.ok(ApiResponse.success("Unread notifications fetched", unread));
    }

    // Mark a specific notification as READ
    @PatchMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(@PathVariable Long id) {
        log.info("API Request: Marking notification {} as read", id);
        notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success("Notification marked as read", null));
    }

    // Bulk mark all notifications for a user as READ
    @PatchMapping("/user/{userId}/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead(@PathVariable Long userId) {
        log.info("API Request: Marking all notifications as read for user {}", userId);
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok(ApiResponse.success("All notifications marked as read", null));
    }
}