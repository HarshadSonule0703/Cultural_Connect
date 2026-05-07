package com.cultureconnect.programgrant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cultureconnect.programgrant.dto.UniversalNotificationRequest;

@FeignClient(name = "notification-alerts-service")
public interface NotificationClient {

    @PostMapping("/api/notifications/send-universal")
    void sendUniversalNotification(@RequestBody UniversalNotificationRequest request);
}
