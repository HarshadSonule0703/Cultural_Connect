package com.cultureconnect.reportandanalysis.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.cultureconnect.reportandanalysis.dto.EventDTO;

@FeignClient(name = "event-resource-service")
public interface EventClient {
    
    // Exact match to their controller
    @GetMapping("/api/events/getallevents")
    List<EventDTO> getAllEvents();
}