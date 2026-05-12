package com.cultureconnect.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cultureconnect.notification.dto.UserDTO;

@FeignClient(name = "auth-service")
public interface UserClient {

    @GetMapping("/cultureconnect/getUserById/{userId}")
    UserDTO getUserById(@PathVariable("userId") Long userId);
}