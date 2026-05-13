package com.cultureconnect.programgrant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cultureconnect.programgrant.dto.CreateNotificationRequest;

@FeignClient(name = "notification-service")
public interface NotificationClient {
    @PostMapping("/api/notifications/send")
    void sendNotification(@RequestBody CreateNotificationRequest request);
}