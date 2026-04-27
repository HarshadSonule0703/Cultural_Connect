package com.cultureconnect.programgrant.entity;

import java.time.LocalDate;

import com.cultureconnect.programgrant.enums.Status;

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
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ENTITY: Grant represents a finalized financial award. Merged to support audit
 * fields (grantCode, organization) and financial integrity.
 */
@Entity
@Table(name = "grants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long grantId;

	@NotBlank(message = "Grant code is required for tracking")
	@Column(unique = true, nullable = false)
	private String grantCode; // Required by ReportServiceImpl.java

	private String organization; // Source or managing body of the grant

	@NotNull(message = "Grant amount is required")
	@Positive(message = "Grant amount must be greater than zero")
	@Column(nullable = false)
	private Double amount; // Standardized to Double to match Program Budget

	@NotNull(message = "Allocation date is required")
	@Column(nullable = false)
	private LocalDate date;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.PENDING;

	// RELATIONSHIPS

	/**
	 * Links the expenditure to a specific government cultural program.
	 */
	@NotNull
	private Long programId;

	/**
	 * Identifies the specific citizen/artist receiving the funds.
	 */
	@NotNull
	private Long citizenId;
}