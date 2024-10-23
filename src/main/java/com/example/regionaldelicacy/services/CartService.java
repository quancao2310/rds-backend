package com.example.regionaldelicacy.services;

import com.example.regionaldelicacy.dto.CartDto;
import com.example.regionaldelicacy.dto.CartItemDto;
import com.example.regionaldelicacy.exceptions.ProductNotFoundException;
import com.example.regionaldelicacy.exceptions.ProductQuantityExceedException;
import com.example.regionaldelicacy.models.Cart;
import com.example.regionaldelicacy.models.Product;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.repositories.CartRepository;
import com.example.regionaldelicacy.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<CartItemDto> getActiveCartsByAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        List<Cart> cartItems = cartRepository.findByUserUserIdAndDeletedFalse(authenticatedUser.getUserId());
        List<CartItemDto> cartItemsInfo = new ArrayList<CartItemDto>();
        for (Cart cartItem : cartItems) {
            cartItemsInfo.add(new CartItemDto(cartItem.getCartId(), cartItem.getProduct().getProductId(),
                    cartItem.getQuantity(), cartItem.getIntoMoney()));
        }
        return cartItemsInfo;
    }

    public CartItemDto addToCart(CartDto cartDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        List<Product> product = this.productRepository.findByProductId(cartDTO.productId());
        if (product == null || product.size() == 0) {
            throw new ProductNotFoundException();
        }
        else if (product.get(0).getStock() < cartDTO.quantity()) {
            throw new ProductQuantityExceedException();
        }
        Cart cart = new Cart();
        cart.setUser(authenticatedUser);
        cart.setProduct(product.get(0));
        cart.setQuantity(cartDTO.quantity());
        cart.setIntoMoney(cartDTO.quantity() * product.get(0).getPrice());
        cart.setDeleted(false);
        Cart cartItem = cartRepository.save(cart);
        return new CartItemDto(cartItem.getCartId(), cartItem.getProduct().getProductId(), cartItem.getQuantity(),
                cartItem.getIntoMoney());
    }

    public void softDeleteCart(Long cartId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        List<Cart> carts = this.cartRepository.findByCartIdAndUserUserIdAndDeletedFalse(cartId,
                authenticatedUser.getUserId());
        for (Cart cart : carts) {
            cart.setDeleted(true);
            cartRepository.save(cart);
        }
    }

}
