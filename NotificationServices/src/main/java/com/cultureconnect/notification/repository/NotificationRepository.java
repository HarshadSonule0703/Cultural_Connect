package com.cultureconnect.notification.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cultureconnect.notification.entity.Notification;
import com.cultureconnect.notification.enums.NotificationStatus;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Query by nested property user.userId
    List<Notification> findByUserIdOrderByCreatedDateDesc(Long userId);

    List<Notification> findByUserIdAndStatus(Long userId, NotificationStatus status);
    
    // NEW FIX: Fetches everything EXCEPT the status you pass in
    List<Notification> findByUserIdAndStatusNot(Long userId, NotificationStatus status);
}