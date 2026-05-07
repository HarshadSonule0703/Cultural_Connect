package com.cultureconnect.programgrant.dto;

import lombok.Data;

@Data
public class UniversalNotificationRequest {

    private Long userId;     // citizenId
    private String email;    // citizen email
    private String message;
    private String category; // PROGRAM / GRANT
    private Long entityId;   // applicationId or programId
}
