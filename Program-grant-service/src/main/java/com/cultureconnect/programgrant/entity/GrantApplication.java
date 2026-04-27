package com.cultureconnect.programgrant.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.cultureconnect.programgrant.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ENTITY: GrantApplication represents a citizen's request for funding under a
 * specific Cultural Program.
 */
@Entity
@Table(name = "grant_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicationId;

	/**
	 * Relationship: The Citizen applying for the grant. optional = false ensures
	 * every application has a valid applicant.
	 */
	@NotNull(message = "Applicant (Citizen) is required")
	@Column(nullable = false)
	private Long citizenId;

	/**
	 * Relationship: The specific Program being targeted. Renamed to 'program' to
	 * match the 'mappedBy' in CulturalProgram entity.
	 */
	@NotNull(message = "Target Cultural Program is required")
	@Column(nullable = false)
	private Long programId;

	/**
	 * Automatically records the exact date and time of submission.
	 */
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private LocalDateTime submittedDate;

	/**
	 * Tracks the lifecycle: PENDING -> APPROVED/REJECTED. Uses the central Status
	 * enum for type safety.
	 */
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Status status = Status.PENDING;
}