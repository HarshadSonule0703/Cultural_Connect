package com.cultureconnect.programgrant.serviceimpl;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultureconnect.programgrant.entity.CulturalProgram;
import com.cultureconnect.programgrant.entity.Grant;
import com.cultureconnect.programgrant.entity.GrantApplication;
import com.cultureconnect.programgrant.enums.Status;
import com.cultureconnect.programgrant.repository.CulturalProgramRepository;
import com.cultureconnect.programgrant.repository.GrantRepository;
import com.cultureconnect.programgrant.service.GrantService;

import lombok.RequiredArgsConstructor;

/**
 * SERVICE IMPLEMENTATION: GrantServiceImpl Handles the final financial
 * disbursement and budget validation logic.
 */
@Service
@RequiredArgsConstructor
public class GrantServiceImpl implements GrantService {



    private final GrantRepository grantRepository;
    private final CulturalProgramRepository programRepository;


    @Override
    @Transactional
    public void disburseGrant(GrantApplication app, Double amount) {

        Long programId = app.getProgramId();

        CulturalProgram program = programRepository.findById(programId)
                .orElseThrow(() -> new RuntimeException("Program not found"));

        Double budget = program.getBudget();

        Double allocated = grantRepository.getTotalAllocatedAmountByProgramId(programId);
        allocated = allocated == null ? 0.0 : allocated;

        if (allocated + amount > budget) {
            throw new RuntimeException("Budget exceeded");
        }

        Grant grant = new Grant();
        grant.setProgramId(programId);
        grant.setCitizenId(app.getCitizenId());
        grant.setAmount(amount);
        grant.setDate(LocalDate.now());
        grant.setStatus(Status.ACTIVE);
        grant.setGrantCode("GRNT-" + UUID.randomUUID().toString()
                            .substring(0, 8).toUpperCase());
        grant.setOrganization("CultureConnect Ministry");

        grantRepository.save(grant);
    }
}
