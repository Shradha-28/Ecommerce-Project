package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.entity.Product;
import com.ecommerce.ecommerce_app.repository.ProductRepository;
import com.ecommerce.ecommerce_app.dto.ApiResponse;
import com.ecommerce.ecommerce_app.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ApiResponse addProduct(Product product) {
        productRepository.save(product);
        return new ApiResponse("Product added", "SUCCESS", product);
    }

    public ApiResponse getAllProducts() {
        return new ApiResponse("Products fetched", "SUCCESS", productRepository.findAll());
    }

    public ApiResponse updateProduct(Long id, Product product) {

        Optional<Product> existing = productRepository.findById(id);

        if (!existing.isPresent()) {
            return new ApiResponse("Product not found", "FAILED", null);
        }

        Product p = existing.get();
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setQuantity(product.getQuantity());

        productRepository.save(p);

        return new ApiResponse("Product updated", "SUCCESS", p);
    }

    public ApiResponse deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            return new ApiResponse("Product not found", "FAILED", null);
        }

        productRepository.deleteById(id);
        return new ApiResponse("Product deleted", "SUCCESS", null);
    }
    @Override
    public ApiResponse saveProduct(Product product) {

        productRepository.save(product);

        return new ApiResponse("Product with image uploaded", "SUCCESS", product);
    }
}