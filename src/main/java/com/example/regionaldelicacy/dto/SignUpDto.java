package com.example.regionaldelicacy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUpDto(
        @NotBlank(message = "Name is mandatory") @Size(min = 1, max = 255) String name,

        @NotBlank(message = "Email is mandatory") @Email(message = "Not a valid email") String email,

        @NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password must be atleast 8 characters") String password,

        @NotBlank(message = "Phonenumber is mandatory") String phoneNumber,

        @NotBlank(message = "Address is mandatory") String address,

        @NotBlank(message = "City is mandatory") String city,

        @NotBlank(message = "Country is mandatory") String country) {

}
