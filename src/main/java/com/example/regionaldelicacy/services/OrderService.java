package com.example.regionaldelicacy.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.regionaldelicacy.dtos.OrderInfoDto;
import com.example.regionaldelicacy.dtos.OrderRequestDto;
import com.example.regionaldelicacy.dtos.OrderInfoDto.OrderItemDto;
import com.example.regionaldelicacy.enums.OrderStatus;
import com.example.regionaldelicacy.enums.PaymentMethod;
import com.example.regionaldelicacy.enums.PaymentStatus;
import com.example.regionaldelicacy.exceptions.CartItemNotValidException;
import com.example.regionaldelicacy.exceptions.DiscountCodeNotValidException;
import com.example.regionaldelicacy.exceptions.OrderNotFoundException;
import com.example.regionaldelicacy.exceptions.PaymentMethodNotSupportedException;
import com.example.regionaldelicacy.exceptions.ProductQuantityExceedException;
import com.example.regionaldelicacy.models.Cart;
import com.example.regionaldelicacy.models.DiscountCode;
import com.example.regionaldelicacy.models.OrderInfo;
import com.example.regionaldelicacy.models.OrderItem;
import com.example.regionaldelicacy.models.Product;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.repositories.CartRepository;
import com.example.regionaldelicacy.repositories.DiscountCodeRepository;
import com.example.regionaldelicacy.repositories.OrderInfoRepository;
import com.example.regionaldelicacy.repositories.ProductRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderInfoRepository orderRepository;
    private final CartRepository cartRepository;
    private final DiscountCodeRepository discountCodeRepository;
    private final ProductRepository productRepository;

    private OrderInfoDto getOrderInfo(OrderInfo order) {
        List<OrderItemDto> items = order.getOrderItems().stream().map(item -> {
            Product productInfo = item.getProduct();
            OrderItemDto itemInfo = OrderItemDto.builder()
                    .productId(productInfo.getId())
                    .name(productInfo.getName())
                    .description(productInfo.getDescription())
                    .price(productInfo.getPrice())
                    .imageUrl(productInfo.getImageUrl())
                    .quantity(item.getQuantity())
                    .intoMoney(item.getIntoMoney())
                    .build();
            return itemInfo;
        }).collect(Collectors.toList());
        OrderInfoDto orderInfo = OrderInfoDto.builder()
                .orderId(order.getId())
                .customerName(order.getCustomerName())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .email(order.getEmail())
                .orderDate(order.getCreatedAt().toString())
                .totalPrice(order.getTotalPrice())
                .items(items)
                .paymentStatus(order.getPaymentStatus())
                .orderStatus(order.getOrderStatus())
                .build();
        if (order.getDiscountCode() != null) {
            orderInfo.setDiscountCode(order.getDiscountCode().getCode());
        }
        return orderInfo;
    }

    @Transactional
    public OrderInfoDto createOrder(OrderRequestDto orderRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        List<Cart> cartItems = cartRepository.findAllByIdInAndDeletedFalse(orderRequest.getCartIds());
        if (cartItems.isEmpty()) {
            throw new CartItemNotValidException();
        }

        if (orderRequest.getPaymentMethod() != PaymentMethod.CASH_ON_DELIVERY) {
            throw new PaymentMethodNotSupportedException();
        }

        OrderInfo order = OrderInfo.builder()
                .customerName(orderRequest.getCustomerName())
                .address(orderRequest.getAddress())
                .phoneNumber(orderRequest.getPhoneNumber())
                .email(orderRequest.getEmail())
                .paymentMethod(orderRequest.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .orderStatus(OrderStatus.PENDING)
                .user(user)
                .build();

        List<OrderItem> orderItems = cartItems.stream().map(cart -> {
            if (cart.getUser().getId() != user.getId())
                throw new CartItemNotValidException();
            Product orderProduct = cart.getProduct();
            if (orderProduct.getStock() < cart.getQuantity()) {
                throw new ProductQuantityExceedException();
            }
            orderProduct.setStock(orderProduct.getStock() - cart.getQuantity());
            productRepository.save(orderProduct);
            return OrderItem.builder()
                    .order(order)
                    .product(cart.getProduct())
                    .quantity(cart.getQuantity())
                    .intoMoney(cart.getProduct().getPrice() * cart.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        double totalPrice = orderItems.stream().mapToDouble(item -> item.getIntoMoney()).sum();

        if (orderRequest.getDiscountCode() != null) {
            DiscountCode discountCode = discountCodeRepository.findByCode(orderRequest.getDiscountCode());
            if (discountCode == null) {
                throw new DiscountCodeNotValidException();
            }
            double discountAmount = totalPrice * (discountCode.getDiscountPercentage() / 100);
            totalPrice -= discountAmount;
            order.setDiscountCode(discountCode);
        }

        order.setTotalPrice(totalPrice);
        OrderInfo newOrder = orderRepository.save(order);

        return getOrderInfo(newOrder);
    }

    public List<OrderInfoDto> getOrdersByUserId(Integer page, Integer size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Pageable paging = PageRequest.of(page, size);
        List<OrderInfo> orders = orderRepository.findByUserId(user.getId(), paging);
        List<OrderInfoDto> orderInfoLst = orders.stream().map(order -> getOrderInfo(order)).collect(Collectors.toList());
        return orderInfoLst;
    }

    public OrderInfoDto getOrderByUserIdAndOrderId(Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        OrderInfo order = orderRepository.findByIdAndUserId(orderId, user.getId());
        if (order == null) {
            throw new OrderNotFoundException();
        }
        return getOrderInfo(order);
    }
}
