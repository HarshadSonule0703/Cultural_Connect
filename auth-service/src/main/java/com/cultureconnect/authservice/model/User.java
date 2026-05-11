package com.cultureconnect.authservice.model;

import com.cultureconnect.authservice.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

	@Id
	//@Min(value = 2500001)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotBlank(message = "Name is mandatory")
	@Column(nullable = false, length = 100,unique = true)
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false,length = 30)
	private Role role;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
	@Column(nullable = false, unique = true, length = 120)
	private String email;

    @NotBlank(message = "Phone number is mandatory")
	@Column(nullable = false, unique = true, length = 15)
	private String phone;

	@Column(nullable = false, length = 20)
	private String status;
	
	//@Min(value = 8)
	@Column(nullable = false)
	@JsonIgnore
	private String password;
}
