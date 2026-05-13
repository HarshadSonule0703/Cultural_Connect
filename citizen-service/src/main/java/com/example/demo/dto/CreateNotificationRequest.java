package com.example.demo.dto;

import com.example.demo.enums.NotificationCategory;

import lombok.Data;

@Data
public class CreateNotificationRequest {
    private Long userId;
    private String email; 
    private Long entityId;
    private NotificationCategory category;
    private String message;
}