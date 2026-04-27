package com.example.demo.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Citizen;
 
public interface CitizenRepository extends JpaRepository<Citizen, Long> {
}
 