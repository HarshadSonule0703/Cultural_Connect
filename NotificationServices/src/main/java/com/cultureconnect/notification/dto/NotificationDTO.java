package com.cultureconnect.notification.dto;

import java.time.LocalDateTime;

import com.cultureconnect.notification.entity.Notification;
import com.cultureconnect.notification.enums.NotificationCategory;
import com.cultureconnect.notification.enums.NotificationStatus;

import lombok.Data;

@Data

public class NotificationDTO {
   private Long notificationId;
   private Long userId;
   private Long entityId;
   private NotificationCategory category;
   private String message;
   private NotificationStatus status;
   private LocalDateTime createdDate;

   public static NotificationDTO fromEntity(Notification notification) {
       NotificationDTO dto = new NotificationDTO();
       dto.setNotificationId(notification.getNotificationId());
       dto.setUserId(notification.getUserId());
       dto.setEntityId(notification.getEntityId());
       dto.setCategory(notification.getCategory());
       dto.setMessage(notification.getMessage());
       dto.setStatus(notification.getStatus());
       dto.setCreatedDate(notification.getCreatedDate());
       return dto;
   }
}
