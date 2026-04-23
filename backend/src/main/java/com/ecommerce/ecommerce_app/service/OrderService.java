package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.dto.ApiResponse;

public interface OrderService {

    ApiResponse placeOrder(Long userId);
    ApiResponse getOrders(Long userId);
}