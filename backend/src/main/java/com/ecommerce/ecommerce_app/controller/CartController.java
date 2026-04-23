package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.entity.Cart;
import com.ecommerce.ecommerce_app.service.CartService;
import com.ecommerce.ecommerce_app.dto.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public ApiResponse add(@RequestBody Cart cart) {
        return cartService.addToCart(cart);
    }

    @GetMapping("/{userId}")
    public ApiResponse getCart(@PathVariable Long userId) {
        return cartService.getUserCart(userId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse remove(@PathVariable Long id) {
        return cartService.removeFromCart(id);
    }
}