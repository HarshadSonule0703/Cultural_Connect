package com.cultureconnect.programgrant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureconnect.programgrant.entity.GrantApplication;
import com.cultureconnect.programgrant.enums.Status;

@Repository
public interface GrantApplicationRepository extends JpaRepository<GrantApplication, Long> {


	// Find all applications for a specific cultural program
	List<GrantApplication> findByProgramId(Long programId);
	
	List<GrantApplication> findByCitizenId(Long citizenId);
	
	// Finds applications filtered by the Status enum
    List<GrantApplication> findByStatus(Status status);
    
   
}