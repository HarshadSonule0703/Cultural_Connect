package com.cultureconnect.notification.service;

public interface EmailService {

    void sendSimpleEmail(String to, String subject, String body);
    void sendOtpEmail(String email, String otp);
}