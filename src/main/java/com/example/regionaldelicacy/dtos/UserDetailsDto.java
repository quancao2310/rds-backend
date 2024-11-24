package com.example.regionaldelicacy.dtos;

import java.time.Instant;

import com.example.regionaldelicacy.models.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDetailsDto {
    private Long userId;
    private String email;
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private Instant lastLogin;
    private String role;

    public UserDetailsDto(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.country = user.getCountry();
        this.lastLogin = user.getLastLogin();
        this.role = user.getRole().name();
    }
}
