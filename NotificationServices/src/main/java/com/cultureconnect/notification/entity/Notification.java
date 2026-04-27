package com.cultureconnect.notification.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.cultureconnect.notification.enums.NotificationCategory;
import com.cultureconnect.notification.enums.NotificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private Long entityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) // Database constraint
    private NotificationCategory category;

    @Column(nullable = false, length = 1000) // Database constraint
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.PENDING;

    @CreationTimestamp
    private LocalDateTime createdDate;
}