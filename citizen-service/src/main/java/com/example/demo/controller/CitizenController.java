package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.CitizenDTO;
import com.example.demo.dto.CitizenDocumentDTO;
import com.example.demo.entity.Citizen;
import com.example.demo.entity.CitizenDocument;
import com.example.demo.service.CitizenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/citizens")
public class CitizenController {

	private final CitizenService service;

	public CitizenController(CitizenService service) {
		this.service = service;
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

}
