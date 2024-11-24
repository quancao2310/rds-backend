package com.example.regionaldelicacy.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dtos.FavoriteAddDto;
import com.example.regionaldelicacy.dtos.FavoriteInfoDto;
import com.example.regionaldelicacy.dtos.PageResponse;
import com.example.regionaldelicacy.dtos.ProductCreateUpdateDto;
import com.example.regionaldelicacy.dtos.ProductDto;
import com.example.regionaldelicacy.models.Product;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.services.ProductService;
import com.example.regionaldelicacy.utils.PaginationUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product API")
public class ProductController {

    private final ProductService productService;

    // Product APIs
    @Operation(summary = "Create a new product", description = "Create a new product with the provided information")
    @ApiResponse(responseCode = "201", description = "Product created")
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    @PostMapping("")
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductCreateUpdateDto productCreateDto) {
        Product product = productService.createProduct(productCreateDto);
        ProductDto productResponse = ProductDto.fromProduct(product);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all products", description = "Retrieve a list of all products with optional search and category filters")
    @ApiResponse(responseCode = "200", description = "List of products")
    @GetMapping("")
    public ResponseEntity<PageResponse<ProductDto>> getAllProducts(
            @Parameter(description = "Category to filter products, can be the ID or the exact-matched, case-insensitive name of the category")
            @RequestParam(required = false) String category,
            @Parameter(description = "Search term to filter products by name")
            @RequestParam(required = false) String search,
            @Parameter(description = "Page number")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PaginationUtils.validateAndCreatePageable(page, size);
        Page<Product> productPage = productService.searchProducts(search, category, pageable);
        Page<ProductDto> productDtoPage = productPage.map(ProductDto::fromProduct);
        return ResponseEntity.ok(PageResponse.of(productDtoPage));
    }

    @Operation(summary = "Get product by id", description = "Retrieve detailed information about a specific product")
    @ApiResponse(responseCode = "200", description = "Product details retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(ProductDto.fromProduct(product));
    }

    @Operation(summary = "Update product", description = "Update a product with the provided information")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @ApiResponse(responseCode = "404", description = "Product or category not found")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(@PathVariable Long id,
            @RequestBody @Valid ProductCreateUpdateDto productUpdateDto) {
        Product product = productService.updateProductById(id, productUpdateDto);
        return ResponseEntity.ok(ProductDto.fromProduct(product));
    }

    @Operation(summary = "Delete product", description = "Delete a product by its ID")
    @ApiResponse(responseCode = "204", description = "Product deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    // Favorite Product APIs
    @Operation(summary = "Get favorite products", description = "Retrieve a list of products that current user has marked as their favorite.")
    @ApiResponse(responseCode = "200", description = "List of favorite products")
    @GetMapping("/favorites")
    public ResponseEntity<PageResponse<FavoriteInfoDto>> getFavoriteProducts(
        @AuthenticationPrincipal User user,
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PaginationUtils.validateAndCreatePageable(page, size);
        Page<FavoriteInfoDto> favoriteProducts = productService.getFavoriteProducts(user, pageable);
        return ResponseEntity.ok(PageResponse.of(favoriteProducts));
    }

    @Operation(summary = "Add favorite product", description = "Add a new product to user's favorite product list")
    @ApiResponse(responseCode = "201", description = "Product added to the list")
    @PostMapping("/favorites")
    public ResponseEntity<FavoriteInfoDto> addFavoriteProduct(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid FavoriteAddDto favoriteAddDto) {
        FavoriteInfoDto favoriteProduct = productService.addFavoriteProduct(user, favoriteAddDto.productId());
        return new ResponseEntity<>(favoriteProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/favorites/{favoriteId}")
    public ResponseEntity<Void> removeFavoriteProduct(
            @AuthenticationPrincipal User user,
            @PathVariable Long favoriteId) {
        productService.removeFavoriteProduct(user, favoriteId);
        return ResponseEntity.noContent().build();
    }
}
