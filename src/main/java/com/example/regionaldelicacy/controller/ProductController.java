package com.example.regionaldelicacy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dto.CreateProductDto;
import com.example.regionaldelicacy.models.Product;
import com.example.regionaldelicacy.services.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Create a new product", description = "Create a new product with the provided information")
    @ApiResponse(responseCode = "201", description = "Product created")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid CreateProductDto productDto) {
        Product product = productDto.toProduct();
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }
    

    @Operation(summary = "Get all products", description = "Retrieve a list of all products or filter by category")
    @ApiResponse(responseCode = "200", description = "List of products")
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts(
            @Parameter(description = "Category to filter products") @RequestParam(required = false) String category) {
        List<Product> products = category == null ? 
            productService.getAllProducts() : 
            productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }
    
    @Operation(summary = "Get product by id", description = "Retrieve detailed information about a specific product")
    @ApiResponse(responseCode = "200", description = "Product details retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
}
