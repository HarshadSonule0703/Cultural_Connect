package com.cultureconnect.notification.dto;

import lombok.Data;

@Data
public class UserReqDTO {
    private Long userId;
    private String email;
    private String name;
}