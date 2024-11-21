package com.example.regionaldelicacy.dtos;

import com.example.regionaldelicacy.validators.ValidPassword;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordDto {
    @NotBlank(message = "Current password is required")
    private String currentPassword;

    @ValidPassword
    private String newPassword;

    @NotBlank(message = "Password confirmation is required")
    private String confirmPassword;
}
