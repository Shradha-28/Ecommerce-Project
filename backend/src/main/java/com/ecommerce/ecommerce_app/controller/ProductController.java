package com.ecommerce.ecommerce_app.controller;

import com.ecommerce.ecommerce_app.entity.Product;
import com.ecommerce.ecommerce_app.service.ProductService;
import com.ecommerce.ecommerce_app.dto.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ApiResponse add(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping
    public ApiResponse getAll() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
    
    @PostMapping("/upload")
    public ApiResponse uploadProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("quantity") int quantity,
            @RequestParam("image") MultipartFile file
    ) {

        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

//            String path = "src/main/resources/static/images/" + fileName;
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/images/";
            String path = uploadDir + fileName;

            file.transferTo(new java.io.File(path));

            Product p = new Product();
            p.setName(name);
            p.setDescription(description);
            p.setPrice(price);
            p.setQuantity(quantity);
            p.setImageUrl("/images/" + fileName);

            // 🔥 FIX HERE
            return productService.saveProduct(p);

        } catch (Exception e) {
            return new ApiResponse("Upload failed", "FAILED", null);
        }
    }
}