package com.cultureconnect.notification.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureconnect.notification.dto.ApiResponse;
import com.cultureconnect.notification.dto.CreateNotificationRequest;
import com.cultureconnect.notification.dto.NotificationDTO;
import com.cultureconnect.notification.dto.OtpRequestDTO;
import com.cultureconnect.notification.dto.CreatelNotificationRequest;
import com.cultureconnect.notification.service.EmailService;
import com.cultureconnect.notification.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final EmailService emailService;
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService, EmailService emailService) {
        this.notificationService = notificationService;
        this.emailService = emailService;
    }

    // --- SENDING NOTIFICATIONS ---

    @PostMapping("/send")
    public ApiResponse<NotificationDTO> send(@Valid @RequestBody CreateNotificationRequest request) {
        return ApiResponse.success("Notification sent successfully", notificationService.sendNotification(request));
    }

    @PostMapping("/universal")
    public ApiResponse<Void> sendUniversal(@Valid @RequestBody CreatelNotificationRequest request) {
        notificationService.sendUniversalNotification(request);
        return ApiResponse.success("Universal notification processed", null);
    }

    @PostMapping("/otp")
    public ApiResponse<Void> sendOtp(@Valid @RequestBody OtpRequestDTO dto) {
        // Validation logic moved to service or kept minimal here
        emailService.sendOtpEmail(dto.getEmail(), dto.getOtp());
        return ApiResponse.success("OTP sent successfully", null);
    }

    // --- RETRIEVAL ---

    @GetMapping
    public ApiResponse<List<NotificationDTO>> getAll() {
        return ApiResponse.success("All notifications fetched", notificationService.getAllNotifications());
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<NotificationDTO>> getByUser(@PathVariable Long userId) {
        return ApiResponse.success("User notifications fetched", notificationService.getUserNotifications(userId));
    }

    // --- ACTIONS ---

    @PutMapping("/{id}/read")
    public ApiResponse<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ApiResponse.success("Notification marked as read", null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ApiResponse.success("Notification deleted", null);
    }
}