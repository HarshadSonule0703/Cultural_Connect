package com.cultureconnect.authservice.serviceimpl;

import org.springframework.stereotype.Service;

import com.cultureconnect.authservice.dto.UserRegisterRequestDTO;
import com.cultureconnect.authservice.model.User;
import com.cultureconnect.authservice.repository.UserRepo;
import com.cultureconnect.authservice.service.UpdateUserService;

@Service
public class UserServiceImpl implements UpdateUserService{

    private final UserRepo repository;

    public UserServiceImpl(UserRepo repository) {
        this.repository = repository;
    }

    @Override
    public void updateUser(UserRegisterRequestDTO dto) {

        User user = repository.findByEmail(dto.getEmail());

        if (user == null) {
            throw new RuntimeException("User not found with email: " + dto.getEmail());
        }

        // ✅ Only allowed fields
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());

        repository.save(user);
    }

   
}
