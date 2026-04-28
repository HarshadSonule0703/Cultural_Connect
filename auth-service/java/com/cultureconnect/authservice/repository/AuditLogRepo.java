package com.cultureconnect.authservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultureconnect.authservice.model.AuditLog;

public interface AuditLogRepo extends JpaRepository<AuditLog, Long> {

	List<AuditLog> findByUser_UserId(Long userId);
}
