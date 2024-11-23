package com.example.regionaldelicacy.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartUpdateDto(
        @NotNull(message = "Cart ID is mandatory") @Positive(message = "The provided cart ID is invalid") Long cartId,
        @NotNull(message = "Quantity is mandatory") @Positive(message = "The provided quantity is invalid") Integer quantity) {
}
