package com.cultureconnect.notification.service;

import java.util.List;

import com.cultureconnect.notification.dto.NotificationDTO;

public interface NotificationService {
    NotificationDTO sendNotification(NotificationDTO dto);
    List<NotificationDTO> getNotificationsForUser(Long userId);
    List<NotificationDTO> getUnreadNotificationsForUser(Long userId);
    void markAsRead(Long notificationId);
    void markAllAsRead(Long userId);
}