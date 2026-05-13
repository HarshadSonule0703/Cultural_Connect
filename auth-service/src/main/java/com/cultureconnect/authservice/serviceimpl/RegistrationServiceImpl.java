package com.cultureconnect.authservice.serviceimpl;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
 
import com.cultureconnect.authservice.dto.UserDTO;
import com.cultureconnect.authservice.enums.Role;
import com.cultureconnect.authservice.model.User;
import com.cultureconnect.authservice.repository.RegistrationLoginRepo;
import com.cultureconnect.authservice.service.RegistrationService;
 
import lombok.AllArgsConstructor;
 
@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
 
	@Autowired
	private RegistrationLoginRepo registrationRepo;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	@Override
	public UserDTO registerUser(UserDTO userDto) {
//		Long userId = System.currentTimeMillis();
		User user = new User();
		System.out.println(userDto.getUserId());
		user.setUserId(userDto.getUserId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		if(userDto.getRole() == null) {
			user.setRole(Role.CITIZEN);
		}else {
			user.setRole(userDto.getRole());
		}
		user.setPhone(userDto.getPhone());
		if(userDto.getStatus() == null) {
			user.setStatus("ACTIVE");
		}else {
			user.setStatus(userDto.getStatus());
		}
		System.out.println(userDto.getPassword());
		user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
		registrationRepo.save(user);
		UserDTO userDTO2 = new UserDTO();
		userDTO2.setUserId(user.getUserId());
		userDTO2.setName(user.getName());
		userDTO2.setEmail(user.getEmail());
		userDTO2.setPhone(user.getPhone());
		userDTO2.setStatus(user.getStatus());
		userDTO2.setRole(user.getRole());
		return userDTO2;
	}

	@Override
	public UserDTO registerUserByAdmin(UserDTO userDto) {
		Long userId = System.currentTimeMillis();
		User user = new User();
		System.out.println(userId);
		user.setUserId(userId);
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		if(userDto.getRole() == null) {
			user.setRole(Role.CITIZEN);
		}else {
			user.setRole(userDto.getRole());
		}
		user.setPhone(userDto.getPhone());
		if(userDto.getStatus() == null) {
			user.setStatus("ACTIVE");
		}else {
			user.setStatus(userDto.getStatus());
		}
		System.out.println(userDto.getPassword());
		user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
		registrationRepo.save(user);
		UserDTO userDTO2 = new UserDTO();
		userDTO2.setUserId(userId);
		userDTO2.setName(user.getName());
		userDTO2.setEmail(user.getEmail());
		userDTO2.setPhone(user.getPhone());
		userDTO2.setStatus(user.getStatus());
		userDTO2.setRole(user.getRole());
		return userDTO2;
	}

	@Override
	public String deleteUserByAdmin(Long userId) {
		User user = registrationRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		if(user.getRole().equals(Role.ADMIN)) {
			throw new UsernameNotFoundException("ADMIN Cannot be deleted");
		}
		registrationRepo.delete(user);
		return "Deleted data successfully";
	}
 
 
}