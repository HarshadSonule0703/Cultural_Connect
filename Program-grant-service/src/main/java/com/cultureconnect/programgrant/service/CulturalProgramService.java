package com.cultureconnect.programgrant.service;

import java.util.List;

import com.cultureconnect.programgrant.dto.CulturalProgramRequestDto;
import com.cultureconnect.programgrant.dto.CulturalProgramResponseDto;

public interface CulturalProgramService {
	CulturalProgramResponseDto createProgram(CulturalProgramRequestDto requestDto);

	List<CulturalProgramResponseDto> getAllPrograms();

	CulturalProgramResponseDto getProgramById(Long id);

	CulturalProgramResponseDto updateProgram(Long id, CulturalProgramRequestDto requestDto);

	void deleteProgram(Long id);
}