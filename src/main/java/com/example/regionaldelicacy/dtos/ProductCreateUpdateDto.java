package com.example.regionaldelicacy.dtos;

import com.example.regionaldelicacy.models.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductCreateUpdateDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Description must be less than 1000 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than 0")
    private Double price;

    @NotNull(message = "Image URL is required")
    private String imageUrl;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than 0")
    private Integer stock;

    public Product toProduct() {
        Product product = new Product();
        product.setName(getName());
        product.setDescription(getDescription());
        product.setPrice(getPrice());
        product.setImageUrl(getImageUrl());
        product.setBrand(getBrand());
        product.setStock(getStock());
        return product;
    }
}
