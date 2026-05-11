package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.dto.CitizenDTO;
import com.example.demo.dto.CitizenDocumentDTO;
import com.example.demo.dto.UniversalNotificationRequest;
import com.example.demo.dto.UpdateCitizenStatusDto;
import com.example.demo.dto.UserRegisterRequestDTO;
import com.example.demo.entity.Citizen;
import com.example.demo.entity.CitizenDocument;
import com.example.demo.enums.Status;
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

	    // ✅ Generate Citizen ID
		Long citizenId = System.currentTimeMillis();
	    // ✅ 1. CALL AUTH-SERVICE
	    UserRegisterRequestDTO userRequest = new UserRegisterRequestDTO();
	    userRequest.setUserId(citizenId);   // ✅ PASS SAME ID
	    userRequest.setName(dto.getName());
	    userRequest.setEmail(dto.getEmail());
	    userRequest.setPhone(dto.getPhone());
	    userRequest.setPassword(dto.getPassword());
	    userRequest.setRole("CITIZEN");

	    try {
	        webClient.post()
	                .uri("http://localhost:9999/cultureconnect/citizenRegister")
	                .bodyValue(userRequest)
	                .retrieve()
	                .bodyToMono(Void.class)
	                .block();
	    } catch (Exception e) {
	        throw new RuntimeException("User creation failed in Auth Service");
	    }

	    // ✅ 2. SAVE CITIZEN
	    Citizen c = new Citizen();
	    c.setCitizenId(citizenId);   // ✅ SET GENERATED ID
	    c.setName(dto.getName());
	    c.setDob(dto.getDob());
	    c.setGender(dto.getGender());
	    c.setAddress(dto.getAddress());
	    c.setPhone(dto.getPhone());
	    c.setEmail(dto.getEmail());
	    c.setStatus(Status.INACTIVE);

	    Citizen savedCitizen = repository.save(c);

	    // ✅ 3. SEND EMAIL (BEST-EFFORT)
	    try {
	        UniversalNotificationRequest notification = new UniversalNotificationRequest();
	        notification.setUserId(savedCitizen.getCitizenId()); // ✅ SAME ID
	        notification.setEmail(savedCitizen.getEmail());
	        notification.setCategory("GENERAL");
	        notification.setEntityId(savedCitizen.getCitizenId());
	        notification.setMessage(
	            "✅ Welcome to CultureConnect!\n\n" +
	            "Dear " + savedCitizen.getName() + ",\n\n" +
	            "Your registration was successful.\n\n" +
	            "Regards,\nCultureConnect Team"
	        );

	        webClient.post()
	                .uri("http://localhost:8085/api/notifications/send-universal")
	                .bodyValue(notification)
	                .retrieve()
	                .bodyToMono(Void.class)
	                .block();

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        System.out.println("⚠ Mail failed, but user created");
	    }

	    return savedCitizen;
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

	    // ✅ 1. Fetch existing citizen
	    Citizen c = getCitizenById(id);

	    // ✅ 2. Update Citizen table
	    c.setName(dto.getName());
	    c.setDob(dto.getDob());
	    c.setGender(dto.getGender());
	    c.setAddress(dto.getAddress());
	    c.setPhone(dto.getPhone());

	    Citizen updatedCitizen = repository.save(c);

	    // ✅ 3. CALL AUTH SERVICE (NEW 🔥)
	    try {
	        UserRegisterRequestDTO userRequest = new UserRegisterRequestDTO();
	        userRequest.setName(dto.getName());
	        userRequest.setEmail(c.getEmail()); // ✅ VERY IMPORTANT (use existing email)
	        userRequest.setPhone(dto.getPhone());

	        // ✅ IMPORTANT: No password update here (unless needed)
	        userRequest.setRole("CITIZEN");

	        webClient.put()
	                .uri("http://localhost:9999/cultureconnect/updateUser")  // ✅ create this API
	                .bodyValue(userRequest)
	                .retrieve()
	                .bodyToMono(Void.class)
	                .block();

	    } catch (Exception e) {
	        e.printStackTrace();
	        System.out.println("⚠ Auth Service update failed, but citizen updated");
	    }

	    return updatedCitizen;
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

	@Override
	public Citizen updateStatus(Long citizenId, UpdateCitizenStatusDto dto) {

	    Citizen citizen = repository.findById(citizenId)
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    "Citizen not found with id: " + citizenId));

	    // ✅ update only status
	    citizen.setStatus(dto.getStatus());

	    return repository.save(citizen);
	}
	@Override
	public Citizen getCitizenByEmail(String email) {
	    return repository.findByEmail(email)
	        .orElseThrow(() -> new RuntimeException("Citizen not found"));
	}


}