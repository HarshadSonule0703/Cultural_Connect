package com.cultureconnect.authservice.dto;

import com.cultureconnect.authservice.enums.Role;

import lombok.Data;

@Data
public class UserDTO {

	private Long userId;
	private String name;
	private Role role;
	private String email;
	private String phone;
	private String status;
	private String password;
	
}
