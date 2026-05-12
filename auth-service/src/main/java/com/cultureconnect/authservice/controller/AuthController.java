package com.cultureconnect.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cultureconnect.authservice.config.JwtTokenUtil;
import com.cultureconnect.authservice.dto.ForgetPasswordDto;
import com.cultureconnect.authservice.dto.JwtResponse;
import com.cultureconnect.authservice.dto.LoginDTO;
import com.cultureconnect.authservice.dto.UserDTO;
import com.cultureconnect.authservice.dto.UserRegisterRequestDTO;
import com.cultureconnect.authservice.dto.UserReqDTO;
import com.cultureconnect.authservice.enums.Role;
import com.cultureconnect.authservice.exception.AuthenticationFailedException;
import com.cultureconnect.authservice.model.User;
import com.cultureconnect.authservice.service.AuditLogService;
import com.cultureconnect.authservice.service.ForgetPasswordService;
import com.cultureconnect.authservice.service.RegistrationService;
import com.cultureconnect.authservice.service.UpdateUserService;
import com.cultureconnect.authservice.serviceimpl.LoginServiceImpl;
import com.cultureconnect.authservice.serviceimpl.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cultureconnect")
@Slf4j
public class AuthController {

	@Autowired
	private AuditLogService auditLogService;

	@Autowired
	private ForgetPasswordService forgetPasswordService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService service;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private LoginServiceImpl loginServiceImpl;

	@Autowired
	private RegistrationService registrationService;

	@PostMapping("/citizenRegister")
	public ResponseEntity<UserDTO> addCitizen(@RequestBody UserDTO userDTO) {
		UserDTO savedDto = registrationService.registerUser(userDTO);
		return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
	}

	@PostMapping("/userRegisterByAdmin")
	public ResponseEntity<UserDTO> addCitizenAsperRole(@RequestBody UserDTO userDTO) {
		UserDTO savedDto = registrationService.registerUserByAdmin(userDTO);
		return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO loginDto) {

	    log.info("Inside Login method {}", loginDto);

	    authenticate(loginDto.getEmail(), loginDto.getPassword());

	    log.info("After authentication");

	    final UserDetails userDetails =
	            loginServiceImpl.loadUserByUsername(loginDto.getEmail());

	    log.info("After userdetails");

	    String role = userDetails.getAuthorities()
	            .stream()
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("Role not found"))
	            .getAuthority()
	            .replace("ROLE_", "");

	    // ✅ FETCH USER TO GET USER ID
	    User user = loginServiceImpl.getUserByEmail(loginDto.getEmail());

	    // ✅ GENERATE TOKEN WITH USER ID INCLUDED
	    final String token = jwtTokenUtil.generateToken(
	            userDetails,
	            Role.valueOf(role),
	            user.getUserId(),     // ✅ THIS IS THE KEY CHANGE
	            user.getName()
	    );

	    log.info("After token generated with userId");

	    try {
	        auditLogService.createAuditLog(user, "LOGIN", "AUTH");
	    } catch (Exception e) {
	        log.error("Audit logging failed", e);
	    }

	    return ResponseEntity.ok(new JwtResponse(token, user.getEmail()));
	}

	private void authenticate(String email, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (BadCredentialsException e) {
			throw new AuthenticationFailedException("INVALID_CREDENTIALS", e);
		}
	}

	@GetMapping("/getUserById/{userId}")
	public UserReqDTO getUserById(@PathVariable Long userId) {
		UserReqDTO userDto = service.getUserDetailsById(userId);
		return userDto;
	}

	@PostMapping("/forgotPassword/otp")
	public ResponseEntity<String> generateOtp(@RequestParam String email) {
		return ResponseEntity.ok(forgetPasswordService.generateOtp(email));
	}

	@PutMapping("/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestBody ForgetPasswordDto dto) {
		return new ResponseEntity<>(forgetPasswordService.resetPassword(dto), HttpStatus.CREATED);
	}

	 private final UserService userService;

	    public AuthController(UserService userService,UpdateUserService updateUserService) {
	        this.userService = userService;
	        this.updateUserService = updateUserService;
	    }

	    @PutMapping("/deactivateUser/{email}")
	    public ResponseEntity<String> deactivateUser(@PathVariable String email) {

	        userService.deactivateUser(email);
	        return ResponseEntity.ok("User deactivated successfully");
	    }

	    @DeleteMapping("/deleteUserByAdmin/{userId}")
		public ResponseEntity<String> deleteUserByAdmin(@PathVariable Long userId) {
			String deletedUser = registrationService.deleteUserByAdmin(userId);
			return new ResponseEntity<>(deletedUser, HttpStatus.NO_CONTENT);
		}
		
		@GetMapping("/getUserByRole/{role}")
		public List<UserReqDTO> getUserByRole(@PathVariable Role role) {
		    return service.getUsersByRole(role);
		}
	    
		private final UpdateUserService updateUserService;
		@PutMapping("/updateUser")
		public ResponseEntity<?> updateUser(@RequestBody UserRegisterRequestDTO dto) {
//		    .updateUser(dto);
			updateUserService.updateUser(dto);
		    return ResponseEntity.ok("User updated");
		}

}
