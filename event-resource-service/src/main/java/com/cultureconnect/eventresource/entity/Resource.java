package com.cultureconnect.eventresource.entity;

import com.cultureconnect.eventresource.enums.ResourceType;
import com.cultureconnect.eventresource.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resource")
public class Resource {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long resourceId;
	
//	@NotBlank(message = "Resource name is required")
	private String name;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ResourceType type; // EQUIPMENT, VENUE, STAFF

	@Positive(message = "Quantity must be at least 1")
	private Integer quantity;

	@Enumerated(EnumType.STRING)
	private Status status; // AVAILABLE, ALLOCATED, DAMAGED

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	private Event event;
}