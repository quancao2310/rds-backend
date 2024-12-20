package com.example.regionaldelicacy.dtos;

import com.example.regionaldelicacy.models.ContactForm;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactFormSubmitDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotBlank(message = "Message is required")
    private String message;

    public ContactForm toContactForm() {
        return ContactForm.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .message(message)
                .build();
    }
}
