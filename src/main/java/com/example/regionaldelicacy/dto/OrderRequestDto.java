package com.example.regionaldelicacy.dto;

import lombok.Data;
import java.util.List;

import com.example.regionaldelicacy.enums.PaymentMethod;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class OrderRequestDto {
    @NotBlank(message = "Customer Name is mandatory")
    @Size(min = 1, max = 255)
    private String customerName;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Phonenumber is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Email is mandatory") 
    @Email(message = "Not a valid email")
    private String email;

    @NotNull(message = "Cart Item ID is mandatory")
    private List<Long> cartIds;

    @NotNull(message = "Payment method is mandatory")
    private PaymentMethod paymentMethod;

    private String discountCode;
}
