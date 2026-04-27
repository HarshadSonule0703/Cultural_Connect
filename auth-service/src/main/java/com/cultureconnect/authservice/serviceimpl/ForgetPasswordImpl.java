package com.cultureconnect.authservice.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cultureconnect.authservice.dto.ForgetPasswordDto;
import com.cultureconnect.authservice.model.User;
import com.cultureconnect.authservice.repository.RegistrationLoginRepo;
import com.cultureconnect.authservice.service.ForgetPasswordService;

@Service
public class ForgetPasswordImpl implements ForgetPasswordService {

	@Autowired
	private RegistrationLoginRepo registrationRepo;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public String resetPassword(ForgetPasswordDto dto) {

		User user = registrationRepo.findByEmail(dto.getEmail())
		        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

		user.setPassword(bcryptEncoder.encode(dto.getPassword()));
		registrationRepo.save(user);

		return "Password updated successfully!";
	}
}