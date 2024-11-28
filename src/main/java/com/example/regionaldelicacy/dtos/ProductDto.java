package com.example.regionaldelicacy.dtos;

import com.example.regionaldelicacy.models.Product;

import lombok.Data;

@Data
public class ProductDto {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
    private String brand;
    private Integer stock;
    private Long favoriteId;

    public ProductDto(Product product, Long favoriteId) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.category = product.getCategory().getName();
        this.brand = product.getBrand();
        this.stock = product.getStock();
        this.favoriteId = favoriteId;
    }

    public static ProductDto fromProduct(Product product) {
        return new ProductDto(product, null);
    }
}
