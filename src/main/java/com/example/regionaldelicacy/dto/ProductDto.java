package com.example.regionaldelicacy.dto;

import com.example.regionaldelicacy.models.Product;

public record ProductDto(
    Long productId,
    String name,
    String description,
    Double price,
    String imageUrl,
    String category,
    String brand,
    Integer stock
) {
    public static ProductDto fromProduct(Product product) {
        return new ProductDto(
            product.getProductId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getImageUrl(),
            product.getCategory().getName(),
            product.getBrand(),
            product.getStock()
        );
    }
}
