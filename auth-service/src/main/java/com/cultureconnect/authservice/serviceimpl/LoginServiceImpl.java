package com.cultureconnect.authservice.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cultureconnect.authservice.dto.AuditLogDto;
import com.cultureconnect.authservice.model.User;
import com.cultureconnect.authservice.repository.RegistrationLoginRepo;

@Service
public class LoginServiceImpl implements UserDetailsService {

	@Autowired
	private RegistrationLoginRepo loginRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

	    User user = loginRepo.findByEmail(email)
	        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
	        throw new DisabledException("User is deactivated");
	    }

	    return new org.springframework.security.core.userdetails.User(
	        user.getEmail(),
	        user.getPassword(),
	        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
	    );
	}

	public AuditLogDto getUserById(String email) {
		User user = loginRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		AuditLogDto auditLogDto = new AuditLogDto();
		auditLogDto.setUserId(user.getUserId());
		return auditLogDto;
	}

	public User getUserByEmail(String email) {
		return loginRepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	}

}
