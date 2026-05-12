package com.cultureconnect.notification.dto;

import java.time.LocalDateTime;

import com.cultureconnect.notification.enums.NotificationCategory;

import lombok.Data;

@Data
public class NotificationDTO {

    private Long notificationId;
    private Long userId;
    private Long entityId;
    private String message;
    private String email;
    private NotificationCategory category;
    private String status;
    private LocalDateTime createdDate;
}
