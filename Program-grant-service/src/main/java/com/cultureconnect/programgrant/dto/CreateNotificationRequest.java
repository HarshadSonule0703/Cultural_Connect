package com.cultureconnect.programgrant.dto;

import com.cultureconnect.programgrant.enums.NotificationCategory;

import lombok.Data;

@Data
public class CreateNotificationRequest {

    private Long userId;     // citizenId
    private String email;    // citizen email
    private String message;
    private NotificationCategory category; // PROGRAM / GRANT
    private Long entityId;   // applicationId or programId
}
