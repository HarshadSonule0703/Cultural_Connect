package com.cultureconnect.programgrant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cultureconnect.programgrant.entity.Grant;

/**
 * Repository interface for the Grant entity.
 * It provides the abstraction layer to perform CRUD operations 
 * and specialized financial calculations on the 'grants' table.
 */
@Repository
public interface GrantRepository extends JpaRepository<Grant, Long> {

    /**
     * Finder method using Property Expressions.
     * Traverses the 'Citizen' relationship to find all grants awarded to a specific person.
     * SQL Equivalent: SELECT * FROM grants WHERE citizen_id = ?
     */
    List<Grant> findByCitizenId(Long citizenId);

    /**
     * Finder method to retrieve all grants issued under a specific Cultural Program.
     * Useful for auditing all disbursements linked to one government initiative.
     * SQL Equivalent: SELECT * FROM grants WHERE program_id = ?
     */
    List<Grant> findByProgramId(Long programId);

    /**
     * Custom JPQL (Java Persistence Query Language) method.
     * Calculates the total sum of money already awarded for a specific program.
     * This is critical for the 'Budget Check' logic in the Service layer 
     * to prevent overspending.
     * * @param programId The ID of the program to check.
     * return The sum of all 'amount' columns, or null if no grants exist yet.
     */

@Query("SELECT SUM(g.amount) FROM Grant g WHERE g.programId = :programId")
    Double getTotalAllocatedAmountByProgramId(Long programId);

	Grant findByCitizenIdAndProgramId(Long citizenId, Long programId);

}