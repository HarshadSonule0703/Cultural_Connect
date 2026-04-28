package com.cultureconnect.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultureconnect.authservice.enums.Role;
import com.cultureconnect.authservice.model.User;

public interface RegistrationLoginRepo extends JpaRepository<User, Long> {

	Optional<User> findByRole(Role role);

	Optional<User> findByEmail(String email);

}
