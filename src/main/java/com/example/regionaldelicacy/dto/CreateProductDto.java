package com.example.regionaldelicacy.dto;

import com.example.regionaldelicacy.models.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductDto(
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,

    @Size(max = 500, message = "Description must be less than 500 characters")
    String description,

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than 0")
    Double price,

    String imageUrl,

    @NotBlank(message = "Category is required")
    String category,

    @NotBlank(message = "Brand is required")
    String brand,

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than 0")
    Integer stock
) {
    public Product toProduct() {
        Product product = new Product();
        product.setName(name());
        product.setDescription(description());
        product.setPrice(price());
        product.setImageUrl(imageUrl());
        product.setCategory(category());
        product.setBrand(brand());
        product.setStock(stock());
        return product;
    }
}