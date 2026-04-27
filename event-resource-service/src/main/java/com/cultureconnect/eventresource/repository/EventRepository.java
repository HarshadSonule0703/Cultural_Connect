package com.cultureconnect.eventresource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cultureconnect.eventresource.entity.Event;
import com.cultureconnect.eventresource.enums.Status;

import feign.Param;

@Repository 
public interface EventRepository extends JpaRepository<Event, Long> {

	List<Event> findByStatus(Status status);

	@Query("SELECT e.eventId FROM Event e WHERE e.programId = :programId")
    List<Long> findEventIdsByProgramId(@Param("programId") Long programId);


}