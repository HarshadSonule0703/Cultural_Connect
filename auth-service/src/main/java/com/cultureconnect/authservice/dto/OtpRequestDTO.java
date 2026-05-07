package com.cultureconnect.authservice.dto;

import lombok.Data;

@Data
public class OtpRequestDTO {
    private String email;
    private String otp;
}
