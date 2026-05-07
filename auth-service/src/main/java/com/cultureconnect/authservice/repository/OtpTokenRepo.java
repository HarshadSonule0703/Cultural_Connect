package com.cultureconnect.authservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cultureconnect.authservice.model.OtpToken;

public interface OtpTokenRepo extends JpaRepository<OtpToken, Long> {

    Optional<OtpToken> findByEmailAndOtpAndUsedFalse(String email, String otp);
}
