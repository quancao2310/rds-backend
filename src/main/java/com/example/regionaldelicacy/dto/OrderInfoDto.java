package com.example.regionaldelicacy.dto;

import java.util.List;

import com.example.regionaldelicacy.enums.OrderStatus;
import com.example.regionaldelicacy.enums.PaymentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderInfoDto {
    private Long orderId;
    private String customerName;
    private String address;
    private String phoneNumber;
    private String email;
    private String orderDate;
    private String discountCode;
    private Double totalPrice;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
    private List<OrderItemDto> items;

    @Data
    @Builder
    public static class OrderItemDto {
        private Long productId;
        private String name;
        private String description;
        private Double price;
        private String imageUrl;
        private Integer quantity;
        private Double intoMoney;
    }
}
