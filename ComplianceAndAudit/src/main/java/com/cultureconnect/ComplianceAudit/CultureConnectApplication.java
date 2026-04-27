package com.cultureconnect.ComplianceAudit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; // Import add koro
@SpringBootApplication
@EnableFeignClients(basePackages = "com.cultureconnect.ComplianceAudit")
@EnableDiscoveryClient 
public class CultureConnectApplication {
    public static void main(String[] args) {
        SpringApplication.run(CultureConnectApplication.class, args);
    }
}