package com.cultureconnect.authservice.service;


import java.util.List;

import com.cultureconnect.authservice.model.AuditLog;
import com.cultureconnect.authservice.model.User;

public interface AuditLogService {

    void createAuditLog(User user, String action, String resource);

    List<AuditLog> getAuditLogsByUser(Long userId);
}

