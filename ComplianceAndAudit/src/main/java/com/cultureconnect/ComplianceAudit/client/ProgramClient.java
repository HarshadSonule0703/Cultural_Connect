package com.cultureconnect.ComplianceAudit.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "program-grant-service", url = "http://localhost:8082")
public interface ProgramClient {
    @GetMapping("/programs")
    Object getProgramById(Long id);
}