package com.cultureconnect.programgrant.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CitizenDTO {

    private Long citizenId;   // ✅ MANDATORY for Feign

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String gender;

    private String address;

    private String email;
}