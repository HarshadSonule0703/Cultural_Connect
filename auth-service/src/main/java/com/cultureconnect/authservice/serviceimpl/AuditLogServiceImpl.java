package com.cultureconnect.authservice.serviceimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cultureconnect.authservice.model.AuditLog;
import com.cultureconnect.authservice.model.User;
import com.cultureconnect.authservice.repository.AuditLogRepo;
import com.cultureconnect.authservice.service.AuditLogService;


@Service
public class AuditLogServiceImpl implements AuditLogService {

	@Autowired
	private AuditLogRepo auditLogRepo;


	 @Override
	    public void createAuditLog(User user, String action, String resource) {

	        AuditLog auditLog = new AuditLog();
	        auditLog.setUser(user);   // ✅ ENTITY reference, NOT id
	        auditLog.setAction(action);
	        auditLog.setResource(resource);
	        auditLog.setTimestamp(new Date());

	        auditLogRepo.save(auditLog);
	    }


	@Override
	public List<AuditLog> getAuditLogsByUser(Long userId) {
		return auditLogRepo.findByUser_UserId(userId);
	}
}