package com.cultureconnect.notification.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureconnect.notification.dto.OtpRequestDTO;
import com.cultureconnect.notification.service.EmailService;

@RestController
@RequestMapping("/api/notifications/otp")
public class OtpNotificationController {

    private final EmailService emailService;

    public OtpNotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    // ✅ Endpoint called by AUTH‑SERVICE
    @PostMapping
    public void sendOtp(@RequestBody OtpRequestDTO request) {

        emailService.sendOtpEmail(
                request.getEmail(),
                request.getOtp()
        );
    }
}
