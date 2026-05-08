package com.example.demo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CitizenDTO {

	@NotBlank(message = "Name is required")
	private String name;

	@Past(message = "DOB cannot be in future")
	private LocalDate dob;

	@NotBlank(message = "Gender is required")
	private String gender;

	@NotBlank(message = "Address is required")
	private String address;

	@Email(message = "Invalid email")
	private String email;
	
	@NotBlank(message = "password is required")
	private String password;


	@Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
	private String phone;

	
}
