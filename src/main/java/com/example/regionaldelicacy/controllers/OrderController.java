package com.example.regionaldelicacy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dtos.OrderInfoDto;
import com.example.regionaldelicacy.dtos.OrderRequestDto;
import com.example.regionaldelicacy.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "API for making and viewing orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Creating a new order from selected user's cart item", 
    description = "Getting a list of ID of the cart items that user selected and the information of the customer to deliver, creating a new order for that user.")
    @ApiResponse(responseCode = "200", description = "Order has been created succesfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderInfoDto.class))
    })
    @PostMapping
    public ResponseEntity<OrderInfoDto> createOrder(@RequestBody @Valid OrderRequestDto orderRequest) {
        OrderInfoDto createdOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(createdOrder);
    }

    @Operation(summary = "Get all orders of current user", description = "Return a list contains info of each orders of current user")
    @ApiResponse(responseCode = "200", description = "Order list has been retrived succesfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderInfoDto[].class))
    })
    @GetMapping
    public ResponseEntity<List<OrderInfoDto>> getOrdersForCurrentUser(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "10") Integer size
    ) {
        List<OrderInfoDto> orders = orderService.getOrdersByUserId(page, size);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get info of an order by orderId", 
    description = "Return the info of the order that matches the provided orderId")
    @ApiResponse(responseCode = "200", description = "Order has been created succesfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = OrderInfoDto.class))
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderInfoDto> getOrderByOrderIdForCurrentUser(@PathVariable Long orderId) {
        OrderInfoDto order = orderService.getOrderByUserIdAndOrderId(orderId);
        return ResponseEntity.ok(order);
    }
}
