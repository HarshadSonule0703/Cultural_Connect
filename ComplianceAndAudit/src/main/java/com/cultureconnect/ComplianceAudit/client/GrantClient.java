package com.cultureconnect.ComplianceAudit.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "grant-client", url = "http://localhost:1241")
public interface GrantClient {
    @GetMapping("/grants/{id}")
    Object getGrantById(@PathVariable("id") Long id);
}