
package com.example.ecommercebackend.controller;

import com.example.ecommercebackend.dto.response.ProductResponse;
import com.example.ecommercebackend.dto.request.ProductRequest;
import com.example.ecommercebackend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Create Product
    @PostMapping("/{categoryId}")
    public ProductResponse createProduct(
           @Valid @RequestBody ProductRequest request,
            @PathVariable Long categoryId) {

        return productService.createProduct(request, categoryId);
    }

    // Get All Products
    @GetMapping
    public List<ProductResponse> getAllProducts() {

        return productService.getAllProducts();
    }

    // Get Product By id
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {

        return productService.getProductById(id);
    }

    // Get Products By Category
    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getProductsByCategory(
            @PathVariable Long categoryId) {

        return productService.getProductsByCategory(categoryId);
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }
}