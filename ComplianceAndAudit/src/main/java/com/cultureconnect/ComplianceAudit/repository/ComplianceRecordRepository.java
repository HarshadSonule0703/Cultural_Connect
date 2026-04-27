package com.cultureconnect.ComplianceAudit.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureconnect.ComplianceAudit.entity.ComplianceRecord;

@Repository
public interface ComplianceRecordRepository extends JpaRepository<ComplianceRecord, Long> {
    List<ComplianceRecord> findByResult(String result);
    List<ComplianceRecord> findByEntityId(Long entityId);
}