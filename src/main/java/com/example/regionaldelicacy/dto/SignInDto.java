package com.example.regionaldelicacy.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignInDto(
        @NotBlank(message = "Email is mandatory") String email,

        @NotBlank(message = "Password is mandatory") @Size(min = 8, message = "Password must be atleast 8 characters") String password) {
}
