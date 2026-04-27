package com.cultureconnect.programgrant.service;

import java.util.List;

import com.cultureconnect.programgrant.dto.GrantApplicationRequestDto;
import com.cultureconnect.programgrant.dto.GrantApplicationResponseDto;
import com.cultureconnect.programgrant.dto.GrantApprovalDto;
import com.cultureconnect.programgrant.enums.Status;

public interface GrantApplicationService {
	GrantApplicationResponseDto submitApplication(GrantApplicationRequestDto requestDto);

	List<GrantApplicationResponseDto> getAllApplications();

	GrantApplicationResponseDto getApplicationById(Long id);

	public GrantApplicationResponseDto updateApplicationStatus(Long id, GrantApprovalDto approvalDto);

	List<GrantApplicationResponseDto> getApplicationsByCitizen(Long citizenId);
	
	List<GrantApplicationResponseDto> getApplicationsByStatus(Status status);
}