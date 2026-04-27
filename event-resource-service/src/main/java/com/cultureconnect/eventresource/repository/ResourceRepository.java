package com.cultureconnect.eventresource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cultureconnect.eventresource.entity.Resource;

import jakarta.transaction.Transactional;
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Resource r WHERE r.event.eventId = :id")
	void deleteByEventId(@Param("id") Long id);
 
}