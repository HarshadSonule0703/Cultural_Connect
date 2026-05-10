package com.example.demo.entity;

import java.time.LocalDate;

import com.example.demo.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "citizen")
public class Citizen {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long citizenId;

	@NotBlank(message = "Name must not be blank")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	@Column(nullable = false, length = 100)
	private String name;

	@NotNull(message = "Date of birth is required")
	@Past(message = "Date of birth must be in the past")
	@Column(nullable = false)
	private LocalDate dob;

	@NotNull(message = "Gender is required")
	@Column(nullable = false, length = 10)
	private String gender;

	@NotBlank(message = "Address must not be blank")
	@Size(max = 255, message = "Address must not exceed 255 characters")
	@Column(nullable = false)
	private String address;

	@NotBlank(message = "Contact info is required")
	@Pattern(regexp = "^[0-9]{10}$|^[A-Za-z0-9+_.-]+@(.+)$", message = "Contact info must be a valid phone number or email")


	 private String phone;
	
	@Column(nullable = false, unique = true)
	private String email;

	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Status is required")
	@Column(nullable = false, length = 20)
	private Status status;
}