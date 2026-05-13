package com.cultureconnect.authservice.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cultureconnect.authservice.dto.UserReqDTO;
import com.cultureconnect.authservice.enums.Role;
import com.cultureconnect.authservice.model.User;
import com.cultureconnect.authservice.repository.RegistrationLoginRepo;
import com.cultureconnect.authservice.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private RegistrationLoginRepo loginRepo;
	
	@Autowired
	private UserRepo repo;

	private final RegistrationLoginRepo userRepository;

	public UserService(RegistrationLoginRepo userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * INTERNAL SERVICE METHOD This method must ONLY fetch user data. DO NOT apply
	 * role or status filtering here.
	 */
	public UserReqDTO getUserDetailsById(Long userId) throws UsernameNotFoundException {

		User user = loginRepo.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not Found with id " + userId));

		// ✅ NO ROLE CHECK
		// ✅ NO STATUS CHECK

		UserReqDTO reqDTO = new UserReqDTO();
		reqDTO.setUserId(user.getUserId());
		reqDTO.setName(user.getName());
		reqDTO.setEmail(user.getEmail());
		reqDTO.setRole(user.getRole());
		reqDTO.setPhone(user.getPhone());

		return reqDTO;
	}

	public void deactivateUser(String email) {

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		user.setStatus("INACTIVE");
		userRepository.save(user);
	}
	
	public List<UserReqDTO> getUsersByRole(Role role) {

	    List<User> users = repo.findByRole(role);

	    return users.stream().map(user -> {
	        UserReqDTO dto = new UserReqDTO();
	        dto.setUserId(user.getUserId());
	        dto.setName(user.getName());
	        dto.setEmail(user.getEmail());
	        dto.setRole(user.getRole());
	        dto.setPhone(user.getPhone());
	        return dto;
	    }).toList();
	}
	
	public List<UserReqDTO> getAllUsers() {
	    // Fetch all users from the database
	    List<User> users = repo.findAll();

	    // Map the List of User entities to a List of UserReqDTOs
	    return users.stream().map(user -> {
	        UserReqDTO dto = new UserReqDTO();
	        dto.setUserId(user.getUserId());
	        dto.setName(user.getName());
	        dto.setEmail(user.getEmail());
	        dto.setRole(user.getRole());
	        dto.setPhone(user.getPhone());
	        // Map status if your DTO has it, as it's useful for the Admin Dashboard
	        return dto;
	    }).toList();
	}

}