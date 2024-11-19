package com.example.regionaldelicacy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FavoriteAddDto(
        @NotNull(message = "Product ID is mandatory") @Positive(message = "The provided product ID is invalid") Long productId) {
}
