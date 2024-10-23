package com.example.regionaldelicacy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDto {

    @NotNull(message = "Product name is required")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters")
    private String productName;

    @Size(max = 500, message = "Description can't exceed 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @NotNull(message = "Category is required")
    private String category;

    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private Integer stock;

    @Size(max = 255, message = "Image URL can't exceed 255 characters")
    private String imageUrl;
}
