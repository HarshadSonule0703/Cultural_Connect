package com.cultureconnect.authservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultureconnect.authservice.enums.Role;
import com.cultureconnect.authservice.model.User;

public interface UserRepo extends JpaRepository<User, Long>{

	List<User> findByRole(Role role);
	
	User findByEmail(String email);
}
