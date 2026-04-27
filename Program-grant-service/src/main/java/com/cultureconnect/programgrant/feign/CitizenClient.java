package com.cultureconnect.programgrant.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cultureconnect.programgrant.dto.CitizenDTO;

@FeignClient(name = "citizen-service")
public interface CitizenClient {

    @GetMapping("/api/citizens/{id}")
    CitizenDTO getCitizenById(@PathVariable Long id);
}
