package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.CreateNotificationRequest;

@FeignClient(name = "NotificationServices")
public interface NotificationClient {
    @PostMapping("/api/notifications/send")
    void sendNotification(@RequestBody CreateNotificationRequest request);
}