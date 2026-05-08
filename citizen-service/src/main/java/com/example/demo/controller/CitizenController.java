 package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.client.ProgramClient;
import com.example.demo.dto.CitizenDTO;
import com.example.demo.dto.CitizenDocumentDTO;
import com.example.demo.dto.CulturalProgramResponseCitizenDto;
import com.example.demo.entity.Citizen;
import com.example.demo.entity.CitizenDocument;
import com.example.demo.service.CitizenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

	private final CitizenService service;

	private final ProgramClient programClientService;
	 
	public CitizenController(CitizenService service, ProgramClient programClientService) {
			this.service = service;
			this.programClientService=programClientService;
		}

	@PostMapping("/register")
	public Citizen register(@Valid @RequestBody CitizenDTO dto) {
		return service.registerCitizen(dto);
	}

	@GetMapping("/{id}")
	public Citizen getById(@PathVariable Long id) {
		return service.getCitizenById(id);
	}

	@GetMapping
	public List<Citizen> getAll() {
		return service.getAllCitizens();
	}

	@PutMapping("/{id}")
	public Citizen update(@PathVariable Long id, @RequestBody CitizenDTO dto) {
		return service.updateCitizen(id, dto);
	}

	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		service.deleteCitizen(id);
		return "Deleted successfully";
	}

	@PostMapping("/{id}/documents")
	public CitizenDocument upload(@PathVariable Long id, @Valid @RequestBody CitizenDocumentDTO dto) {

		return service.uploadDocument(id, dto);
	}

	@GetMapping("/{id}/documents")
	public List<CitizenDocument> getDocs(@PathVariable Long id) {
		return service.getDocuments(id);
	}

	@DeleteMapping("/documents/{docId}")
	public String deleteDoc(@PathVariable Long docId) {
		service.deleteDocument(docId);
		return "Document deleted successfully";
	}
	@GetMapping("/getAllCitizenProgram")
	public ResponseEntity<List<CulturalProgramResponseCitizenDto>> getAllCitizenProgram() {
		return programClientService.getAllProgramsForCitizen();
	}
	
	 

}
