package com.example.regionaldelicacy.dtos;

public record CartItemDto(
        Long cartId,
        Long productId,
        Integer quantity,
        Double intoMoney) {

}
