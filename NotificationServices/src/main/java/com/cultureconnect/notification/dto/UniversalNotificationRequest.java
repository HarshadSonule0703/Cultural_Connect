package com.cultureconnect.notification.dto;

import com.cultureconnect.notification.enums.NotificationCategory;

import lombok.Data;

@Data
public class UniversalNotificationRequest {

	private Long userId;
	private String email;
	private NotificationCategory category;
	private String message;
	private Long entityId; // optional
}