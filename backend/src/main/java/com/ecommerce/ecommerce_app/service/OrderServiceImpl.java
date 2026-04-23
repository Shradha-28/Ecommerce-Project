package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.entity.*;
import com.ecommerce.ecommerce_app.repository.*;
import com.ecommerce.ecommerce_app.dto.ApiResponse;
import com.ecommerce.ecommerce_app.service.OrderService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    @Override
    public ApiResponse placeOrder(Long userId) {

        List<Cart> cartItems = cartRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            return new ApiResponse("Cart is empty", "FAILED", null);
        }

        double total = 0;

        // 🔴 STEP 1: CHECK ALL STOCK FIRST
        for (Cart c : cartItems) {

            Product p = productRepository.findById(c.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (p.getQuantity() < c.getQuantity()) {
                return new ApiResponse(
                        "Not enough stock for product: " + p.getName(),
                        "FAILED",
                        null
                );
            }
        }

        // 🔴 STEP 2: CREATE ORDER
        Order order = new Order();
        order.setUserId(userId);
        order = orderRepository.save(order);

        // 🔴 STEP 3: PROCESS ORDER
        for (Cart c : cartItems) {

            Product p = productRepository.findById(c.getProductId()).get();

            double itemTotal = p.getPrice() * c.getQuantity();
            total += itemTotal;

            // Reduce stock
            p.setQuantity(p.getQuantity() - c.getQuantity());
            productRepository.save(p);

            // Save order item
            OrderItem item = new OrderItem();
            item.setOrderId(order.getId());
            item.setProductId(p.getId());
            item.setQuantity(c.getQuantity());
            item.setPrice(p.getPrice());

            orderItemRepository.save(item);
        }

        // 🔴 STEP 4: UPDATE TOTAL
        order.setTotalAmount(total);
        orderRepository.save(order);

        // 🔴 STEP 5: CLEAR CART
        cartRepository.deleteAll(cartItems);

        return new ApiResponse("Order placed", "SUCCESS", order);
    }
    @Override
    public ApiResponse getOrders(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        // 🔥 attach items
        for (Order o : orders) {
            o.setItems(orderItemRepository.findByOrderId(o.getId()));
        }

        return new ApiResponse("Orders fetched", "SUCCESS", orders);
    }}