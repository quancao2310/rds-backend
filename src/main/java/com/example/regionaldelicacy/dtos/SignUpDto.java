package com.example.regionaldelicacy.dtos;

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
    private String email;

    @NotBlank(message = "Password is mandatory")
    @ValidPassword
    private String password;
}
