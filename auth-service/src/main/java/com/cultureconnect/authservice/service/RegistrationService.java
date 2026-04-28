package com.cultureconnect.authservice.service;

import com.cultureconnect.authservice.dto.UserDTO;

public interface RegistrationService {

	public UserDTO registerUser(UserDTO userDto);
	public String deleteUserByAdmin(Long userId) ;
}
