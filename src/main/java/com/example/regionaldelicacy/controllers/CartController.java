package com.example.regionaldelicacy.controllers;

import com.example.regionaldelicacy.dtos.CartAddDto;
import com.example.regionaldelicacy.dtos.CartItemDto;
import com.example.regionaldelicacy.dtos.CartUpdateDto;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.services.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @ApiResponse(responseCode = "200", description = "Item list has been retrieved succesfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CartItemDto.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<CartItemDto>> getCartByAuthenticatedUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getActiveCartsByAuthenticatedUser(user));
    }

    @Operation(summary = "Add an item in user's cart", description = "Add a product with its quantity to user's cart")
    @ApiResponse(responseCode = "201", description = "Item has been added succesfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemDto.class))
    })
    @PostMapping("")
    public ResponseEntity<CartItemDto> addToCart(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CartAddDto cartAddDto) {
        return new ResponseEntity<>(cartService.addToCart(cartAddDto, user), HttpStatus.CREATED);
    }

    @Operation(summary = "Update the quantity of an item in user's cart", description = "Update the quantity of an item with the correspond cartId in user's cart")
    @ApiResponse(responseCode = "200", description = "Cart updated successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CartItemDto.class))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CartItemDto> updateCartItem(
            @PathVariable Long id,
            @RequestBody @Valid CartUpdateDto cartUpdateDto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.updateCartItem(id, cartUpdateDto, user));
    }

    @Operation(summary = "Remove an item out of user's cart", description = "Remove an item with the correspond cartId out of user's cart")
    @ApiResponse(responseCode = "204", description = "Cart deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long id, @AuthenticationPrincipal User user) {
        cartService.softDeleteCart(id, user);
        return ResponseEntity.noContent().build();
    }
}
