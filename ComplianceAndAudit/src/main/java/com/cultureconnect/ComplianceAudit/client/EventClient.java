package com.cultureconnect.ComplianceAudit.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cultureconnect.ComplianceAudit.dto.EventDTO;


@FeignClient(name = "event-resource-service", url = "http://localhost:8086")
public interface EventClient {

    @GetMapping("/events/{programId}")
    List<EventDTO> getEventsByProgramId(@PathVariable("programId") Long programId); 
}