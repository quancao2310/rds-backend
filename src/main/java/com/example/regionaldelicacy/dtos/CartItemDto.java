package com.example.regionaldelicacy.dtos;

import com.example.regionaldelicacy.models.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDto {
    private Long cartId;
    private Integer quantity;
    private Double intoMoney;
    private ProductDto product;

    public static CartItemDto fromCart(Cart cart) {
        return new CartItemDto(
                cart.getId(),
                cart.getQuantity(),
                cart.getIntoMoney(),
                ProductDto.fromProduct(cart.getProduct()));
    }
}
