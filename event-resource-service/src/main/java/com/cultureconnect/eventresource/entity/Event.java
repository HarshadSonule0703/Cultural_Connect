package com.cultureconnect.eventresource.entity;

import java.time.LocalDate;
import java.util.List;

import com.cultureconnect.eventresource.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventId;

	@NotBlank(message = "Event title is mandatory")
	private String title;

	@NotBlank(message = "Location is required")
	private String location;

	@FutureOrPresent(message = "Event date cannot be in the past")
	private LocalDate date;
	

	@Enumerated(EnumType.STRING)
	private Status status; // PLANNED, ONGOING, COMPLETED, CANCELLED

	@Column(nullable = false)
	private Long programId;

	@OneToMany(mappedBy = "event")
	private List<Resource> resources;
}