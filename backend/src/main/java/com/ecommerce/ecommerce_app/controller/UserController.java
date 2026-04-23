package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.entity.User;
import com.ecommerce.ecommerce_app.service.UserService;
import com.ecommerce.ecommerce_app.dto.ApiResponse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ApiResponse register(@Valid @RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ApiResponse login(@RequestBody User user) {
        return userService.loginUser(user);
    }

    @GetMapping
    public ApiResponse getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}