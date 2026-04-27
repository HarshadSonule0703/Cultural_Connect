package com.cultureconnect.ComplianceAudit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.cultureconnect.ComplianceAudit.dto.ComplianceResponseDTO;
import com.cultureconnect.ComplianceAudit.entity.NewProgram;

@Repository
public interface NewProgramRepository extends JpaRepository<NewProgram, Long> {

	boolean existsByProgramId(Long entityId);
	@Modifying
    void deleteByProgramId(Long entityId);
    
    ComplianceResponseDTO getByProgramId(Long programId);

}