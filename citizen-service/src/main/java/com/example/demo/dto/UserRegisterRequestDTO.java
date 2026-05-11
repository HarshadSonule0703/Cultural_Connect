package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDTO {

		private Long userId;
	 private String name;
	    private String email;
	    private String phone;
	    private String password;
	    private String role;   // "CITIZEN"

	    // getters & setters

}