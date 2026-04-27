package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Citizen;
import com.example.demo.entity.CitizenDocument;
import com.example.demo.dto.CitizenDTO;
import com.example.demo.dto.CitizenDocumentDTO;

public interface CitizenService {

	Citizen registerCitizen(CitizenDTO dto);

	Citizen getCitizenById(Long id);

	List<Citizen> getAllCitizens();

	Citizen updateCitizen(Long id, CitizenDTO dto);

	void deleteCitizen(Long id);

	CitizenDocument uploadDocument(Long id, CitizenDocumentDTO dto);

	List<CitizenDocument> getDocuments(Long id);

	void deleteDocument(Long documentId);
}