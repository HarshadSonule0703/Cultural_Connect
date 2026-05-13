package com.example.demo.repository;
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Citizen;
import com.example.demo.enums.Status;
 
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
	Optional<Citizen> findByEmail(String email);
	List<Citizen> findByStatus(Status status);
	
}
 