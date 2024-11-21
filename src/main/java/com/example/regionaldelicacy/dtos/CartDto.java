package com.example.regionaldelicacy.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartDto(
        @NotNull(message = "Product ID cannot be null") Long productId,

        @NotNull(message = "Quantity cannot be null") @Positive(message = "Quantity must be positive") Integer quantity) {
}
