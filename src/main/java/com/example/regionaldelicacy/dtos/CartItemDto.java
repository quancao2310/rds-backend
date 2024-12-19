package com.example.regionaldelicacy.dtos;

import com.example.regionaldelicacy.models.Cart;
import com.example.regionaldelicacy.models.Product;

import lombok.Data;

@Data
public class CartItemDto {
    private Long cartId;
    private Long productId;
    private String productName;
    private String productImageUrl;
    private Double unitPrice;
    private Integer quantity;
    private Double totalPrice;

    public static CartItemDto fromCart(Cart cart) {
        Product product = cart.getProduct();
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCartId(cart.getId());
        cartItemDto.setProductId(product.getId());
        cartItemDto.setProductName(product.getName());
        cartItemDto.setProductImageUrl(product.getImageUrl());
        cartItemDto.setUnitPrice(product.getPrice());
        cartItemDto.setQuantity(cart.getQuantity());
        cartItemDto.setTotalPrice(cart.getQuantity() * product.getPrice());
        return cartItemDto;
    }
}
