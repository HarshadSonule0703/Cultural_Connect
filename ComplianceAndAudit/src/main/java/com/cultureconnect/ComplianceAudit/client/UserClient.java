package com.cultureconnect.ComplianceAudit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// If you are using Eureka, use name = "user-service" (the spring.application.name)
// If not using Eureka, keep the URL
@FeignClient(name = "user-service", url = "http://localhost:8081") 
public interface UserClient {
    
    @GetMapping("/users/{id}")
    Object getUserById(@PathVariable("id") Long id);
}