package com.cultureconnect.authservice.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cultureconnect.authservice.client.NotificationClient;
import com.cultureconnect.authservice.dto.ForgetPasswordDto;
import com.cultureconnect.authservice.dto.OtpNotificationDto;
import com.cultureconnect.authservice.model.User;
import com.cultureconnect.authservice.repository.RegistrationLoginRepo;
import com.cultureconnect.authservice.service.ForgetPasswordService;

@Service
public class ForgetPasswordImpl implements ForgetPasswordService {

	@Autowired
	private NotificationClient client;

	@Autowired
	private RegistrationLoginRepo registrationRepo;

	@Autowired
	private OtpService otpService;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public String resetPassword(ForgetPasswordDto dto) {

		otpService.validateOtp(dto.getEmail(), dto.getOtp());

		User user = registrationRepo.findByEmail(dto.getEmail())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		user.setPassword(bcryptEncoder.encode(dto.getPassword()));
		registrationRepo.save(user);

		return "Password updated successfully!";
	}

	@Override
	public String generateOtp(String email) {

		registrationRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		String otp = otpService.generateOtp(email);

		OtpNotificationDto dto = new OtpNotificationDto();
		dto.setEmail(email);
		dto.setOtp(otp);

		client.sendOtp(dto);

		// TODO: Send OTP via Email/SMS
		System.out.println("✅ OTP for " + email + " = " + otp);

		return "OTP sent successfully";
}
}