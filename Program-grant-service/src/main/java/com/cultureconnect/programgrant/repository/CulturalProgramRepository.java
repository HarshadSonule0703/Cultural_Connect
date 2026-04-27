package com.cultureconnect.programgrant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureconnect.programgrant.entity.CulturalProgram;
 
@Repository
public interface CulturalProgramRepository extends JpaRepository<CulturalProgram, Long> {
	
}