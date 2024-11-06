package com.example.regionaldelicacy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dto.OrderInfoDto;
import com.example.regionaldelicacy.dto.OrderRequestDto;
import com.example.regionaldelicacy.services.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderInfoDto> createOrder(@RequestBody @Valid OrderRequestDto orderRequest) {
        OrderInfoDto createdOrder = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderInfoDto>> getOrdersForCurrentUser() {
        List<OrderInfoDto> orders = orderService.getOrdersByUserId();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderInfoDto> getOrderByOrderIdForCurrentUser(@PathVariable Long orderId) {
        OrderInfoDto order = orderService.getOrderByUserIdAndOrderId(orderId);
        return ResponseEntity.ok(order);
    }
}
