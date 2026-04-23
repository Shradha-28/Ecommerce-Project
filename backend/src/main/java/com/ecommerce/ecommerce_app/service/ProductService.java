package com.ecommerce.ecommerce_app.service;

import com.ecommerce.ecommerce_app.entity.Product;
import com.ecommerce.ecommerce_app.dto.ApiResponse;

public interface ProductService {

    ApiResponse addProduct(Product product);
    ApiResponse getAllProducts();
    ApiResponse updateProduct(Long id, Product product);
    ApiResponse deleteProduct(Long id);
    ApiResponse saveProduct(Product product);
}