package com.example.regionaldelicacy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInDto {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;
}
