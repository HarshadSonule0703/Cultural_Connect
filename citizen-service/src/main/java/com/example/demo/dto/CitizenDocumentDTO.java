package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
public class CitizenDocumentDTO {

    @NotBlank(message = "Document type is required")
    private String docType;

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
}