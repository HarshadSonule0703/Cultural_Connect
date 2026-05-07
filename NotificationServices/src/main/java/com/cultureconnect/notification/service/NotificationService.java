package com.cultureconnect.notification.service;

import java.util.List;

import com.cultureconnect.notification.dto.CreateNotificationRequest;
import com.cultureconnect.notification.dto.NotificationDTO;
import com.cultureconnect.notification.dto.UniversalNotificationRequest;

public interface NotificationService {

	NotificationDTO sendNotification(CreateNotificationRequest request);

	List<NotificationDTO> getAllNotifications();

	List<NotificationDTO> getUserNotifications(Long userId);

	void markAsRead(Long notificationId);

	void deleteNotification(Long id);

	void sendUniversalNotification(UniversalNotificationRequest request);

}