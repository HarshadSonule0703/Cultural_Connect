package com.cultureconnect.ComplianceAudit.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cultureconnect.ComplianceAudit.entity.User;
 
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}