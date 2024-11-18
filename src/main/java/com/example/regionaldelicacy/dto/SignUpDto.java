package com.example.regionaldelicacy.dto;

import com.example.regionaldelicacy.validators.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SignUpDto {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Not a valid email")
    String email;

    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    String password;

    @NotBlank(message = "Phonenumber is mandatory")
    String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    String address;

    @NotBlank(message = "City is mandatory")
    String city;

    @NotBlank(message = "Country is mandatory")
    String country;
}
