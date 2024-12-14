package com.example.regionaldelicacy.services;

import com.example.regionaldelicacy.dtos.CartAddDto;
import com.example.regionaldelicacy.dtos.CartItemDto;
import com.example.regionaldelicacy.dtos.CartUpdateDto;
import com.example.regionaldelicacy.exceptions.CartItemNotValidException;
import com.example.regionaldelicacy.exceptions.ProductNotFoundException;
import com.example.regionaldelicacy.exceptions.ProductQuantityExceedException;
import com.example.regionaldelicacy.models.Cart;
import com.example.regionaldelicacy.models.Product;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.repositories.CartRepository;
import com.example.regionaldelicacy.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public List<CartItemDto> getActiveCartsByAuthenticatedUser(User user) {
        List<Cart> cartItems = cartRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(user.getId());
        return cartItems.stream()
                .map(CartItemDto::fromCart)
                .toList();
    }

    public CartItemDto addToCart(CartAddDto cartAddDto, User user) {
        Product product = productRepository.findById(cartAddDto.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        if (product.getStock() < cartAddDto.getQuantity()) {
            throw new ProductQuantityExceedException();
        }

        Cart cart = cartRepository.findByProductIdAndUserIdAndDeletedFalse(product.getId(), user.getId());
        if (cart == null) {
            cart = Cart.builder()
                    .quantity(cartAddDto.getQuantity())
                    .user(user)
                    .product(product)
                    .deleted(false)
                    .build();
        } else {
            cart.setQuantity(cart.getQuantity() + cartAddDto.getQuantity());
        }

        Cart cartItem = cartRepository.save(cart);
        return CartItemDto.fromCart(cartItem);
    }

    public CartItemDto updateCartItem(Long id, CartUpdateDto cartUpdateDto, User user) {
        Cart cart = cartRepository.findByIdAndUserIdAndDeletedFalse(id, user.getId())
                .orElseThrow(CartItemNotValidException::new);
        Product product = cart.getProduct();
        if (product.getStock() < cartUpdateDto.getQuantity()) {
            throw new ProductQuantityExceedException();
        }
        cart.setQuantity(cartUpdateDto.getQuantity());
        Cart updatedCart = cartRepository.save(cart);
        return CartItemDto.fromCart(updatedCart);
    }

    public void softDeleteCart(Long id, User user) {
        Cart cart = cartRepository.findByIdAndUserIdAndDeletedFalse(id, user.getId())
                .orElseThrow(CartItemNotValidException::new);
        cart.setDeleted(true);
        cartRepository.save(cart);
    }
}
