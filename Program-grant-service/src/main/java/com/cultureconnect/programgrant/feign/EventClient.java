package com.cultureconnect.programgrant.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-resource-service")
public interface EventClient {

    @GetMapping("/api/events/program/{programId}")
    List<Long> getEventIdsByProgram(@PathVariable Long programId);
}

