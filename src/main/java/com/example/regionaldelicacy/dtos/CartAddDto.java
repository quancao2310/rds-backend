package com.example.regionaldelicacy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartAddDto {
    @NotBlank(message = "Product ID is required")
    private Long productId;

    @NotBlank(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}
