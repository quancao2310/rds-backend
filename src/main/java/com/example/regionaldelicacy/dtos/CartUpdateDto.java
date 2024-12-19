package com.example.regionaldelicacy.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartUpdateDto {
    @NotBlank(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;
}
