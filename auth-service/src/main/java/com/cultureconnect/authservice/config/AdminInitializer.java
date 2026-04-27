package com.cultureconnect.authservice.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cultureconnect.authservice.enums.Role;
import com.cultureconnect.authservice.model.User;
import com.cultureconnect.authservice.repository.RegistrationLoginRepo;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createAdmin(
            RegistrationLoginRepo userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.findByRole(Role.ADMIN).isEmpty()) {

                User admin = new User();
                admin.setName("System Admin");
                admin.setEmail("admin@cultureconnect.gov");
                admin.setPhone("9000000000");
                admin.setRole(Role.ADMIN);
                admin.setStatus("ACTIVE");
                admin.setPassword(passwordEncoder.encode("Admin@123"));

                userRepository.save(admin);

                System.out.println("✅ CULTURECONNECT ADMIN created successfully");
            } else {
                System.out.println("ℹ️ CULTURECONNECT ADMIN already exists");
            }
        };
    }
}