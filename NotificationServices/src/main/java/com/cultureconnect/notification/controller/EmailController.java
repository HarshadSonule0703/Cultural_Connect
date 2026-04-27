package com.cultureconnect.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureconnect.notification.dto.EmailRequest;
import com.cultureconnect.notification.service.EmailService;
import com.cultureconnect.notification.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
       
    @PostMapping("/test-send")
    public ResponseEntity<ApiResponse<String>> testEmail(@RequestBody EmailRequest request) { 
        emailService.sendSimpleEmail(request.getTo(),request.getSubject() , request.getMessage());
        return ResponseEntity.ok(ApiResponse.success("Email sent successfully", null));
    }
}

