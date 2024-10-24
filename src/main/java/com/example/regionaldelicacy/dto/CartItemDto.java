package com.example.regionaldelicacy.dto;

public record CartItemDto(
        Long cartId,
        Long productId,
        Integer quantity,
        Double intoMoney) {

}
