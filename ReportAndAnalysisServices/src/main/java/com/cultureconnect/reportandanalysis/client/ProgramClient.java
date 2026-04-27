package com.cultureconnect.reportandanalysis.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.cultureconnect.reportandanalysis.dto.ProgramDTO;

@FeignClient(name = "PROGRAM-SERVICE")
public interface ProgramClient {

    // Exact match to their controller
    @GetMapping("/api/programs/getAllProgram")
    List<ProgramDTO> getAllPrograms();
}