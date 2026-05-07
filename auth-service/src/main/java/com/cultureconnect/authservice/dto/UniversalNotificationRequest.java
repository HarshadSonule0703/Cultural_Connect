package com.cultureconnect.authservice.dto;

import lombok.Data;

@Data
public class UniversalNotificationRequest {

    private Long userId;
    private String email;
    private String message;
    private String category; // e.g. GENERAL, COMPLIANCE
    private Long entityId;   // optional
}
