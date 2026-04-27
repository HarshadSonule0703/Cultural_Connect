package com.cultureconnect.ComplianceAudit.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cultureconnect.ComplianceAudit.dto.AuditRequestDTO;
import com.cultureconnect.ComplianceAudit.dto.AuditResponseDTO;
import com.cultureconnect.ComplianceAudit.entity.Audit;
import com.cultureconnect.ComplianceAudit.exception.ResourceNotFoundException;
import com.cultureconnect.ComplianceAudit.repository.AuditRepository;
import com.cultureconnect.ComplianceAudit.repository.UserRepository;
import com.cultureconnect.ComplianceAudit.service.AuditService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private static final Logger logger =
            LoggerFactory.getLogger(AuditServiceImpl.class);

    private final AuditRepository auditRepo;
    private final UserRepository userRepository;

    @Override
    public AuditResponseDTO createAudit(AuditRequestDTO dto) {
        logger.info("Creating audit for scope: {}", dto.getScope());

        // ✅ Validate Audit Officer using UserRepository
        if (!userRepository.existsById(dto.getOfficerId())) {
            logger.error("Audit Officer not found with ID: {}", dto.getOfficerId());
            throw new ResourceNotFoundException(
                    "Audit Officer not found with ID: " + dto.getOfficerId());
        }

        // ✅ Map DTO → Entity
        Audit audit = new Audit();
        audit.setScope(dto.getScope());
        audit.setFindings(dto.getFindings());
        audit.setDate(dto.getDate());
        audit.setStatus(dto.getStatus());
        audit.setOfficerId(dto.getOfficerId());

        Audit saved = auditRepo.save(audit);
        return mapToDTO(saved);
    }

    @Override
    public List<AuditResponseDTO> getAllAudits() {
        logger.info("Fetching all audits");
        return auditRepo.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuditResponseDTO getAuditById(Long id) {
        Audit audit = auditRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Audit not found with ID: " + id));
        return mapToDTO(audit);
    }

    @Override
    public AuditResponseDTO updateAudit(Long id, AuditRequestDTO dto) {
        Audit audit = auditRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Audit not found with ID: " + id));

        // ✅ Re‑validate officer only if changed
        if (!audit.getOfficerId().equals(dto.getOfficerId())) {
            if (!userRepository.existsById(dto.getOfficerId())) {
                throw new ResourceNotFoundException(
                        "Audit Officer not found with ID: " + dto.getOfficerId());
            }
        }

        audit.setScope(dto.getScope());
        audit.setFindings(dto.getFindings());
        audit.setDate(dto.getDate());
        audit.setStatus(dto.getStatus());
        audit.setOfficerId(dto.getOfficerId());

        return mapToDTO(auditRepo.save(audit));
    }

    @Override
    public void deleteAudit(Long id) {
        if (!auditRepo.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Cannot delete. Audit not found with ID: " + id);
        }
        auditRepo.deleteById(id);
        logger.info("Audit deleted successfully with ID: {}", id);
    }

    // ✅ Mapping Helper
    private AuditResponseDTO mapToDTO(Audit audit) {
        AuditResponseDTO dto = new AuditResponseDTO();
        dto.setAuditId(audit.getAuditId());
        dto.setScope(audit.getScope());
        dto.setFindings(audit.getFindings());
        dto.setDate(audit.getDate());
        dto.setStatus(audit.getStatus());
        dto.setOfficerName("Officer ID: " + audit.getOfficerId());
        return dto;
    }
}
