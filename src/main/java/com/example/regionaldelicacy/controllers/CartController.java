package com.example.regionaldelicacy.controllers;

import com.example.regionaldelicacy.dtos.CartDto;
import com.example.regionaldelicacy.dtos.CartItemDto;
import com.example.regionaldelicacy.dtos.CartUpdateDto;
import com.example.regionaldelicacy.services.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
@Tag(name = "Cart", description = "API for managing user's cart")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "Get all items in user's cart", description = "Return a list contains basic info of each items in user's cart")
    @ApiResponse(responseCode = "200", description = "Item list has been retrived succesfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemDto[].class))
    })
    @GetMapping
    public ResponseEntity<List<CartItemDto>> getCartByAuthenticatedUser(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok(cartService.getActiveCartsByAuthenticatedUser(page, size));
    }

    @Operation(summary = "Add an item in user's cart", description = "Add a product with its quantity to user's cart")
    @ApiResponse(responseCode = "200", description = "Item has been added succesfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemDto.class))
    })
    @PostMapping
    public ResponseEntity<CartItemDto> addToCart(@Valid @RequestBody CartDto cartDTO) {
        CartItemDto cart = cartService.addToCart(cartDTO);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Update the quantity of an item in user's cart", description = "Update the quantity of an item with the correspond cartId in user's cart")
    @ApiResponse(responseCode = "200", description = "Cart updated successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemDto.class))
    })
    @PutMapping
    public ResponseEntity<CartItemDto> updateCartItem(@RequestBody @Valid CartUpdateDto cartUpdateDto) {
        CartItemDto cart = cartService.updateCartItem(cartUpdateDto);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Remove an item out of user's cart", description = "Remove an item with the correspond cartId out of user's cart")
    @ApiResponse(responseCode = "200", description = "Cart deleted successfully")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable @Parameter(description = "ID of the item in user's cart to be deleted", required = true) Long cartId) {
        cartService.softDeleteCart(cartId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
