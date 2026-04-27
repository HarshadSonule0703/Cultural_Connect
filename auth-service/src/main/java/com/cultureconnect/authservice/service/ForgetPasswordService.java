package com.cultureconnect.authservice.service;

import com.cultureconnect.authservice.dto.ForgetPasswordDto;

public interface ForgetPasswordService {
	String resetPassword(ForgetPasswordDto dto);
}
