package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.dto.CitizenDTO;
import com.example.demo.dto.CitizenDocumentDTO;
import com.example.demo.dto.UserRegisterRequestDTO;
import com.example.demo.entity.Citizen;
import com.example.demo.entity.CitizenDocument;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.CitizenDocumentRepository;
import com.example.demo.repository.CitizenRepository;
import com.example.demo.service.CitizenService;

@Service
public class CitizenServiceImpl implements CitizenService {

	private final CitizenRepository repository;
	private final CitizenDocumentRepository documentRepository;
	private final WebClient webClient;

	public CitizenServiceImpl(CitizenRepository repository, CitizenDocumentRepository documentRepository,
			WebClient webClient) {
		this.repository = repository;
		this.documentRepository = documentRepository;
		this.webClient = webClient;
	}

	// ✅ REGISTER CITIZEN

	@Override
	public Citizen registerCitizen(CitizenDTO dto) {

		// ✅ 1. CALL AUTH-SERVICE
		UserRegisterRequestDTO userRequest = new UserRegisterRequestDTO();
		userRequest.setName(dto.getName());
		userRequest.setEmail(dto.getEmail()); // using email/phone
		userRequest.setPhone(dto.getPhone());
		userRequest.setPassword("Citizen@123"); // default (can improve later)
		userRequest.setRole("CITIZEN");

		try {
			webClient.post().uri("http://localhost:9999/cultureconnect/citizenRegister").bodyValue(userRequest)
					.retrieve().bodyToMono(Void.class).block();
		} catch (Exception e) {
			throw new RuntimeException("User creation failed in Auth Service");
		}

		// ✅ 2. SAVE CITIZEN
		Citizen c = new Citizen();
		c.setName(dto.getName());
		c.setDob(dto.getDob());
		c.setGender(dto.getGender());
		c.setAddress(dto.getAddress());

		c.setPhone(dto.getPhone());
		c.setEmail(dto.getEmail());

		c.setStatus("ACTIVE");

		return repository.save(c);
	}

	// ✅ GET BY ID
	@Override
	public Citizen getCitizenById(Long id) {

		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Citizen not found with ID: " + id));
	}

	// ✅ GET ALL
	@Override
	public List<Citizen> getAllCitizens() {
		return repository.findAll();
	}

	// ✅ UPDATE CITIZEN
	@Override
	public Citizen updateCitizen(Long id, CitizenDTO dto) {

		Citizen c = getCitizenById(id);

		c.setName(dto.getName());
		c.setDob(dto.getDob());
		c.setGender(dto.getGender());
		c.setAddress(dto.getAddress());
		c.setPhone(dto.getPhone());
		c.setEmail(dto.getEmail());

		return repository.save(c);
	}

	// ✅ DELETE CITIZEN

	@Override
	public void deleteCitizen(Long id) {

		Citizen citizen = getCitizenById(id);

		// ✅ 1. Call auth-service to deactivate user
		webClient.put().uri("http://localhost:9999/cultureconnect/deactivateUser/" + citizen.getEmail())
				.retrieve().bodyToMono(Void.class).block();

		// ✅ 2. Delete citizen data
		repository.delete(citizen);
	}

	// ✅ UPLOAD DOCUMENT (FIXED ✅)
	@Override
	public CitizenDocument uploadDocument(Long id, CitizenDocumentDTO dto) {

		// ✅ Ensure citizen exists (business validation)
		getCitizenById(id);

		CitizenDocument doc = new CitizenDocument();
		doc.setCitizenId(id); // ✅ FIX
		doc.setDocType(dto.getDocType());
		doc.setFileUri(dto.getFileUri());
		doc.setVerificationStatus("PENDING");

		return documentRepository.save(doc);
	}

	// ✅ GET DOCUMENTS (FIXED ✅)
	@Override
	public List<CitizenDocument> getDocuments(Long id) {

		return documentRepository.findByCitizenId(id); // ✅ FIX
	}

	// ✅ DELETE DOCUMENT
	@Override
	public void deleteDocument(Long documentId) {

		CitizenDocument doc = documentRepository.findById(documentId)
				.orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

		documentRepository.delete(doc);
	}
}