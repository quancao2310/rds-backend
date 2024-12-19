package com.example.regionaldelicacy.dtos;

import com.example.regionaldelicacy.models.Address;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressCreateUpdateDto {
    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Postal code is required")
    private String postalCode;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @NotBlank(message = "Recipient name is required")
    private String customerName;

    public Address toAddress() {
        return Address.builder()
                .address(this.address)
                .city(this.city)
                .country(this.country)
                .postalCode(this.postalCode)
                .phoneNumber(this.phoneNumber)
                .customerName(this.customerName)
                .build();
    }
}
