package com.cultureconnect.notification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cultureconnect.notification.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j // Enables professional logging
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    /**
     * Sends a simple text email.
     * @param toEmail Recipient address
     * @param subject Email subject line
     * @param body Content of the email
     */
    public void sendSimpleEmail(String toEmail, String subject, String body) {
        try {
            log.info("Attempting to send email to {}", toEmail);
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(senderEmail);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            
            mailSender.send(message);
            log.info("Email sent successfully to {}", toEmail);
        } catch (Exception e) {
            // Log the error but don't stop the whole application
            log.error("SMTP Error: Failed to send email to {}. Reason: {}", toEmail, e.getMessage());
            throw new ResourceNotFoundException("Email service currently unavailable");
        }
    }
}