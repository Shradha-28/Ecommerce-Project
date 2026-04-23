package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.dto.ApiResponse;
import com.ecommerce.ecommerce_app.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{userId}")
    public ApiResponse placeOrder(@PathVariable Long userId) {
        return orderService.placeOrder(userId);
    }

    @GetMapping("/{userId}")
    public ApiResponse getOrders(@PathVariable Long userId) {
        return orderService.getOrders(userId);
    }
}