package com.example.regionaldelicacy.dtos;

import com.example.regionaldelicacy.models.Address;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String address;
    private String city;
    private String country;
    private String postalCode;
    private String phoneNumber;
    private String customerName;
    private Long userId;

    public AddressDto(Address address) {
        this.id = address.getId();
        this.address = address.getAddress();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.postalCode = address.getPostalCode();
        this.phoneNumber = address.getPhoneNumber();
        this.customerName = address.getCustomerName();
        this.userId = address.getUser().getId();
    }
}
