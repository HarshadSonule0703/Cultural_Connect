package com.cultureconnect.eventresource.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cultureconnect.eventresource.dto.ProgramDto;

@FeignClient(name = "program-grant-service")
public interface ProgramClient {

    @GetMapping("/api/programs/getProgram/{id}")
    ProgramDto getProgramById(@PathVariable Long id);
}
