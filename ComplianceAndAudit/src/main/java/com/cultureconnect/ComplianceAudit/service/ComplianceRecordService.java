package com.cultureconnect.ComplianceAudit.service;
 
import java.util.List;
 
import com.cultureconnect.ComplianceAudit.entity.NewProgram;
import com.cultureconnect.ComplianceAudit.dto.ComplianceRequestDTO;
import com.cultureconnect.ComplianceAudit.dto.ComplianceResponseDTO;
import com.cultureconnect.ComplianceAudit.dto.CulturalProgramRequestDto;
import com.cultureconnect.ComplianceAudit.dto.CulturalProgramResponseDto;
 
public interface ComplianceRecordService {
    ComplianceResponseDTO createCompliance(ComplianceResponseDTO requestDto);
    List<ComplianceResponseDTO> getAllCompliance();
    List<NewProgram> getNewPrograms();
    ComplianceResponseDTO getComplianceById(Long id);
    ComplianceResponseDTO updateCompliance(Long id, ComplianceRequestDTO requestDto);
    void deleteCompliance(Long id);
	CulturalProgramResponseDto addNewProgram(CulturalProgramResponseDto dto);
}