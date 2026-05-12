package com.cultureconnect.programgrant.serviceimpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cultureconnect.programgrant.dto.CulturalProgramRequestDto;
import com.cultureconnect.programgrant.dto.CulturalProgramResponseCitizenDto;
import com.cultureconnect.programgrant.dto.CulturalProgramResponseDto;
import com.cultureconnect.programgrant.entity.CulturalProgram;
import com.cultureconnect.programgrant.entity.Grant;
import com.cultureconnect.programgrant.entity.GrantApplication;
import com.cultureconnect.programgrant.exception.ResourceNotFoundException;
import com.cultureconnect.programgrant.feign.EventClient;
import com.cultureconnect.programgrant.repository.CulturalProgramRepository;
import com.cultureconnect.programgrant.repository.GrantApplicationRepository;
import com.cultureconnect.programgrant.repository.GrantRepository;
import com.cultureconnect.programgrant.service.CulturalProgramService;

import lombok.RequiredArgsConstructor;

/**
 * SERVICE IMPLEMENTATION: CulturalProgramServiceImpl
 * Manages the lifecycle of cultural initiatives.
 * Standardized to use 'name' and 'grantApplications' to match the Entity.
 */
@Service
@RequiredArgsConstructor 
public class CulturalProgramServiceImpl implements CulturalProgramService {

    private static final Logger logger = LoggerFactory.getLogger(CulturalProgramServiceImpl.class);

    private final CulturalProgramRepository programRepository;
    private final GrantApplicationRepository applicationRepository;
    private final GrantRepository grantRepository;
    private final EventClient eventClient;


    /**
     * Creates a new Cultural Program.
     * Maps 'title' from DTO to 'name' in Entity.
     */
    @Override
    @Transactional
    public CulturalProgramResponseDto createProgram(CulturalProgramRequestDto dto) {
        logger.info("Service: Creating program: {}", dto.getTitle());
        
        CulturalProgram program = mapToEntity(dto);
        CulturalProgram savedProgram = programRepository.save(program);
        
        return mapToResponseDto(savedProgram);
    }

    @Override
    public List<CulturalProgramResponseDto> getAllPrograms() {
        return programRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public CulturalProgramResponseDto getProgramById(Long id) {
        CulturalProgram program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with ID: " + id));
        return mapToResponseDto(program);
    }
    
    @Override
	public List<CulturalProgramResponseCitizenDto> getAllProgramsForCitizen() {
    	return programRepository.findAll().stream()
                .map(this::mapToCitizenResponseDto)
                .toList();
	}
 

    /**
     * Updates an existing program.
     * Fix: Uses .setName() to match the Entity field.
     */
    @Override
    @Transactional
    public CulturalProgramResponseDto updateProgram(Long id, CulturalProgramRequestDto dto) {
        CulturalProgram program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program not found with ID: " + id));
        
        // Mapping DTO title to Entity name
        program.setName(dto.getTitle()); 
        program.setDescription(dto.getDescription());
        program.setStartDate(dto.getStartDate());
        program.setEndDate(dto.getEndDate());
        program.setBudget(dto.getBudget());
        program.setStatus(dto.getStatus());

        return mapToResponseDto(programRepository.save(program));
    }

    @Override
    @Transactional
    public void deleteProgram(Long id) {
        if (!programRepository.existsById(id)) {
            throw new ResourceNotFoundException("Program not found with ID: " + id);
        }
        programRepository.deleteById(id);
    }

    // --- PRIVATE MAPPING METHODS ---

    /**
     * Maps Request DTO -> Entity.
     * Note: dto.getTitle() is mapped to entity.setName().
     */
    private CulturalProgram mapToEntity(CulturalProgramRequestDto dto) {
        CulturalProgram entity = new CulturalProgram();
        entity.setName(dto.getTitle()); // Match Entity field 'name'
        entity.setDescription(dto.getDescription());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setBudget(dto.getBudget());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    /**
     * Maps Entity -> Response DTO.
     * Fix: Uses .getGrantApplications() to match the Entity relationship name.
     */
    private CulturalProgramResponseDto mapToResponseDto(CulturalProgram program) {

        Long programId = program.getProgramId();

        List<Long> applicationIds =
                applicationRepository.findByProgramId(programId)
                        .stream()
                        .map(GrantApplication::getApplicationId)
                        .toList();

        List<Long> grantIds =
                grantRepository.findByProgramId(programId)
                        .stream()
                        .map(Grant::getGrantId)
                        .toList();

        List<Long> eventIds;
        try {
            eventIds = eventClient.getEventIdsByProgram(programId);
        } catch (Exception ex) {
            eventIds = List.of(); // graceful fallback
        }

        return new CulturalProgramResponseDto(
                programId,
                program.getName(),
                program.getDescription(),
                program.getStartDate(),
                program.getEndDate(),
                program.getBudget(),
                program.getStatus(),
                applicationIds,
                grantIds,
                eventIds
        );
    }
    private CulturalProgramResponseCitizenDto mapToCitizenResponseDto(CulturalProgram program) {
    	 
        Long programId = program.getProgramId();
        System.out.println("Budget: " + program.getBudget());

 
        return new CulturalProgramResponseCitizenDto(
                programId,
                program.getName(),
                program.getDescription(),
                program.getStartDate(),
                program.getEndDate(),
                program.getBudget()        );
    }
}