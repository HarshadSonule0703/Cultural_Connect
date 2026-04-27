package com.cultureconnect.ComplianceAudit.service;

import java.util.List;

import com.cultureconnect.ComplianceAudit.dto.AuditRequestDTO;
import com.cultureconnect.ComplianceAudit.dto.AuditResponseDTO;

public interface AuditService {
    AuditResponseDTO createAudit(AuditRequestDTO dto);
    List<AuditResponseDTO> getAllAudits();
    AuditResponseDTO getAuditById(Long id);
    AuditResponseDTO updateAudit(Long id, AuditRequestDTO dto);
    void deleteAudit(Long id);
}