package com.example.regionaldelicacy.controller;

import com.example.regionaldelicacy.dto.ProductDto;
import com.example.regionaldelicacy.models.Product;
import com.example.regionaldelicacy.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto productDTO) {
        Product product = new Product();
        product.setProductName(productDTO.productName());
        product.setDescription(productDTO.description());
        product.setPrice(productDTO.price());
        product.setCategory(productDTO.category());
        product.setStock(productDTO.stock());
        product.setImageUrl(productDTO.imageUrl());
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

}
