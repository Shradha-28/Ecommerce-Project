package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.entity.User;
import com.ecommerce.ecommerce_app.repository.UserRepository;
import com.ecommerce.ecommerce_app.dto.ApiResponse;
import com.ecommerce.ecommerce_app.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new ApiResponse("Email already exists!", "FAILED", null);
        }

        userRepository.save(user);
        return new ApiResponse("User registered successfully", "SUCCESS", user);
    }

    @Override
    public ApiResponse loginUser(User user) {

        Optional<User> existing = userRepository.findByEmail(user.getEmail());

        if (!existing.isPresent()) {
            return new ApiResponse("User not found", "FAILED", null);
        }

        if (!existing.get().getPassword().equals(user.getPassword())) {
            return new ApiResponse("Invalid password", "FAILED", null);
        }

        // 🔥 RETURN FULL USER (IMPORTANT)
        return new ApiResponse("Login successful", "SUCCESS", existing.get());
    }
    @Override
    public ApiResponse getAllUsers() {
        List<User> users = userRepository.findAll();
        return new ApiResponse("Users fetched", "SUCCESS", users);
    }

    @Override
    public ApiResponse getUserById(Long id) {

        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            return new ApiResponse("User not found", "FAILED", null);
        }

        return new ApiResponse("User found", "SUCCESS", user.get());
    }

    @Override
    public ApiResponse updateUser(Long id, User user) {

        Optional<User> existing = userRepository.findById(id);

        if (!existing.isPresent()) {
            return new ApiResponse("User not found", "FAILED", null);
        }

        User u = existing.get();
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());

        userRepository.save(u);

        return new ApiResponse("User updated", "SUCCESS", u);
    }

    @Override
    public ApiResponse deleteUser(Long id) {

        if (!userRepository.existsById(id)) {
            return new ApiResponse("User not found", "FAILED", null);
        }

        userRepository.deleteById(id);
        return new ApiResponse("User deleted", "SUCCESS", null);
    }
}