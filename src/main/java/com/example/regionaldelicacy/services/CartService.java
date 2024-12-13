package com.example.regionaldelicacy.services;

import com.example.regionaldelicacy.dtos.CartDto;
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

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public List<CartItemDto> getActiveCartsByAuthenticatedUser(Integer page, Integer size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        Pageable paging = PageRequest.of(page, size);
        List<Cart> cartItems = cartRepository.findByUserIdAndDeletedFalseOrderByCreatedAtDesc(authenticatedUser.getId(), paging);
        List<CartItemDto> cartItemsInfo = new ArrayList<CartItemDto>();
        for (Cart cartItem : cartItems) {
            cartItemsInfo.add(CartItemDto.fromCart(cartItem));
        }
        return cartItemsInfo;
    }

    public CartItemDto addToCart(CartDto cartDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        Product product = productRepository.findById(cartDTO.productId()).orElseThrow(ProductNotFoundException::new);
        if (product.getStock() < cartDTO.quantity()) {
            throw new ProductQuantityExceedException();
        }
        Cart cart = cartRepository.findByProductIdAndUserIdAndDeletedFalse(product.getId(),
                authenticatedUser.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(authenticatedUser);
            cart.setProduct(product);
            cart.setQuantity(cartDTO.quantity());
            cart.setIntoMoney(cartDTO.quantity() * product.getPrice());
            cart.setDeleted(false);
        } else {
            cart.setQuantity(cart.getQuantity() + cartDTO.quantity());
            cart.setIntoMoney(cart.getQuantity() * product.getPrice());
        }
        Cart cartItem = cartRepository.save(cart);
        return CartItemDto.fromCart(cartItem);
    }

    public CartItemDto updateCartItem(CartUpdateDto cartUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        Cart cart = cartRepository.findByIdAndUserIdAndDeletedFalse(cartUpdateDto.cartId(),
                authenticatedUser.getId());
        if (cart == null) {
            throw new CartItemNotValidException();
        }
        Product product = cart.getProduct();
        if (product.getStock() < cartUpdateDto.quantity()) {
            throw new ProductQuantityExceedException();
        }
        cart.setQuantity(cartUpdateDto.quantity());
        cart.setIntoMoney(cartUpdateDto.quantity() * product.getPrice());
        Cart updatedCart = cartRepository.save(cart);
        return CartItemDto.fromCart(updatedCart);
    }

    public void softDeleteCart(Long cartId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        Cart cart = cartRepository.findByIdAndUserIdAndDeletedFalse(cartId,
                authenticatedUser.getId());
        if (cart == null) {
            throw new CartItemNotValidException();
        }
        cart.setDeleted(true);
        cartRepository.save(cart);
    }
}
