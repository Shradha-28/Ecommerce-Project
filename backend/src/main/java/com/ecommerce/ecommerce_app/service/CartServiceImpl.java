package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.entity.Cart;
import com.ecommerce.ecommerce_app.repository.CartRepository;
import com.ecommerce.ecommerce_app.dto.ApiResponse;
import com.ecommerce.ecommerce_app.service.CartService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    public ApiResponse addToCart(Cart cart) {

        List<Cart> existingItems = cartRepository.findByUserId(cart.getUserId());

        for (Cart c : existingItems) {

            // 🔥 If same product exists → increase quantity
            if (c.getProductId().equals(cart.getProductId())) {
                c.setQuantity(c.getQuantity() + cart.getQuantity());
                cartRepository.save(c);
                return new ApiResponse("Quantity updated", "SUCCESS", c);
            }
        }

        // 🔥 Else → add new item
        cartRepository.save(cart);
        return new ApiResponse("Added to cart", "SUCCESS", cart);
    }
    public ApiResponse getUserCart(Long userId) {
        return new ApiResponse("Cart fetched", "SUCCESS",
                cartRepository.findByUserId(userId));
    }

    public ApiResponse removeFromCart(Long id) {
        cartRepository.deleteById(id);
        return new ApiResponse("Removed from cart", "SUCCESS", null);
    }
}