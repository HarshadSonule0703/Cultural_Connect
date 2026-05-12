package com.example.demo.dto;

import lombok.Data;

@Data
public class UniversalNotificationRequest{

    private Long userId;
    private String email;
    private String message;
    private String category;
    private Long entityId;
}
