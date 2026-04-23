package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.entity.Cart;
import com.ecommerce.ecommerce_app.dto.ApiResponse;

public interface CartService {

    ApiResponse addToCart(Cart cart);
    ApiResponse getUserCart(Long userId);
    ApiResponse removeFromCart(Long id);
}