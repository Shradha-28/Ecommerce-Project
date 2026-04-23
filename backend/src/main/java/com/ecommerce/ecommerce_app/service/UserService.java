package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.entity.User;
import com.ecommerce.ecommerce_app.dto.ApiResponse;

import java.util.List;

public interface UserService {

    ApiResponse registerUser(User user);

    ApiResponse loginUser(User user);

    ApiResponse getAllUsers();

    ApiResponse getUserById(Long id);

    ApiResponse updateUser(Long id, User user);

    ApiResponse deleteUser(Long id);
}