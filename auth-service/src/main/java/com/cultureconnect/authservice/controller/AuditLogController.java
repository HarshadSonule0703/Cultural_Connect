package com.cultureconnect.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cultureconnect.authservice.model.AuditLog;
import com.cultureconnect.authservice.service.AuditLogService;


@RestController
@RequestMapping("/audit_log")
public class AuditLogController {
	
	@Autowired
	private AuditLogService auditLogService;

	 @GetMapping("/getLogById/{userId}")
	    public List<AuditLog> getAuditLogs(@PathVariable Long userId) {
	        return auditLogService.getAuditLogsByUser(userId);
	    }
	 @GetMapping("/getAll")
	 public List<AuditLog> getAllLogs() {
	     return auditLogService.getAllAuditLogs();
	 }
}
