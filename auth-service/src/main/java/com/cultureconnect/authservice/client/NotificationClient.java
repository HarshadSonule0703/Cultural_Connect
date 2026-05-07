package com.cultureconnect.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cultureconnect.authservice.dto.OtpNotificationDto;

@FeignClient(name = "NotificationServices")
public interface NotificationClient {

	 @PostMapping("/api/notifications/sendOtp")
	    void sendOtp(@RequestBody OtpNotificationDto dto);
	    
	    @PostMapping
	    void sendRegistrationMessage(@RequestBody String email);
}
