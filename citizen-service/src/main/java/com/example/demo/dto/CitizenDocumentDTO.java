package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class CitizenDocumentDTO {

    @NotBlank(message = "Document type is required")
    private String docType;

    @NotBlank(message = "File URI is required")
    private String fileUri;

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getFileUri() {
        return fileUri;
    }

    public void setFileUri(String fileUri) {
        this.fileUri = fileUri;
    }
}