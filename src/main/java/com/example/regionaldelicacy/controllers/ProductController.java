package com.example.regionaldelicacy.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dtos.CreateProductDto;
import com.example.regionaldelicacy.dtos.FavoriteAddDto;
import com.example.regionaldelicacy.dtos.FavoriteInfoDto;
import com.example.regionaldelicacy.dtos.ProductDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid CreateProductDto productDto) {
        Product product = productService.saveProduct(productDto);
        ProductDto productResponse = ProductDto.fromProduct(product);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all products", description = "Retrieve a list of all products with optional search and category filters")
    @ApiResponse(responseCode = "200", description = "List of products")
    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts(
            @Parameter(description = "Category to filter products, can be the ID or the exact-matched, case-insensitive name of the category") @RequestParam(required = false) String category,
            @Parameter(description = "Search term to filter products by name") @RequestParam(required = false) String search) {
        List<Product> products = productService.searchProducts(search, category);
        List<ProductDto> productDtos = products.stream()
                .map(ProductDto::fromProduct)
                .toList();
        return ResponseEntity.ok(productDtos);
    }

    @Operation(summary = "Get product by id", description = "Retrieve detailed information about a specific product")
    @ApiResponse(responseCode = "200", description = "Product details retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ProductDto.fromProduct(product));
    }

    @Operation(summary = "Get favorite products", description = "Retrieve a list of products that current user has marked as their favorite.")
    @ApiResponse(responseCode = "200", description = "List of favorite products")
    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteInfoDto>> getFavoriteProducts(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size
    ) {
        List<FavoriteInfoDto> favoriteProducts = productService.getFavoriteProducts(page, size);
        return ResponseEntity.ok(favoriteProducts);
    }

    @Operation(summary = "Add favorite product", description = "Add a new product to user's favorite product list")
    @ApiResponse(responseCode = "201", description = "Product added to the list")
    @PostMapping("/favorites")
    public ResponseEntity<FavoriteInfoDto> addFavoriteProduct(@RequestBody @Valid FavoriteAddDto favoriteAddDto) {
        FavoriteInfoDto favoriteProduct = productService.addFavoriteProduct(favoriteAddDto.productId());
        return new ResponseEntity<>(favoriteProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/favorites/{favoriteId}")
    public ResponseEntity<Void> removeFavoriteProduct(@PathVariable Long favoriteId) {
        productService.removeFavoriteProduct(favoriteId);
        return ResponseEntity.ok().build();
    }

}
