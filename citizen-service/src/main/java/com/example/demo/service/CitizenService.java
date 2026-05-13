package com.example.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.CitizenDTO;
import com.example.demo.dto.UpdateCitizenStatusDto;
import com.example.demo.entity.Citizen;
import com.example.demo.entity.CitizenDocument;
import com.example.demo.enums.Status;

public interface CitizenService {

	Citizen registerCitizen(CitizenDTO dto);

	Citizen getCitizenById(Long id);

	List<Citizen> getAllCitizens();

	Citizen updateCitizen(Long id, CitizenDTO dto);

	void deleteCitizen(Long id);

	public CitizenDocument uploadDocument(Long id, MultipartFile file, String docType);

	List<CitizenDocument> getDocuments(Long id);

	void deleteDocument(Long documentId);
	
	Citizen updateStatus(Long citizenId,UpdateCitizenStatusDto dto);
	
	public Citizen getCitizenByEmail(String email);
	
	public List<Citizen> getCitizensByStatus(Status status);
	
}