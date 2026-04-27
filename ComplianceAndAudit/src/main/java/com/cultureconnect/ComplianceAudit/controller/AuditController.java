package com.cultureconnect.ComplianceAudit.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cultureconnect.ComplianceAudit.dto.AuditRequestDTO;
import com.cultureconnect.ComplianceAudit.dto.AuditResponseDTO;
import com.cultureconnect.ComplianceAudit.service.AuditService;

import java.util.List;

@RestController
@RequestMapping("/audits")
@RequiredArgsConstructor
public class AuditController {

    private final AuditService auditService;

    @PostMapping("/create")
    public ResponseEntity<AuditResponseDTO> createAudit(@Valid @RequestBody AuditRequestDTO dto) {
        return ResponseEntity.ok(auditService.createAudit(dto));
    }

    @GetMapping("/list")
    public ResponseEntity<List<AuditResponseDTO>> getAllAudits() {
        return ResponseEntity.ok(auditService.getAllAudits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditResponseDTO> getAuditById(@PathVariable Long id) {
        return ResponseEntity.ok(auditService.getAuditById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<AuditResponseDTO> updateAudit(@PathVariable Long id, @Valid @RequestBody AuditRequestDTO dto) {
        return ResponseEntity.ok(auditService.updateAudit(id, dto));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteAudit(@PathVariable Long id) {
        auditService.deleteAudit(id);
        return ResponseEntity.ok("Audit deleted successfully with ID: " + id);
    }
}