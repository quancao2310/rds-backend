package com.example.regionaldelicacy.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dtos.ContactFormSubmitDto;
import com.example.regionaldelicacy.models.ContactForm;
import com.example.regionaldelicacy.services.ContactFormService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contact-us")
@RequiredArgsConstructor
@Tag(name = "Contact us", description = "APIs for sending contact form")
public class ContactFormController {
    private final ContactFormService service;

    @PostMapping("")
    public ResponseEntity<ContactForm> submitContactForm(@RequestBody @Valid ContactFormSubmitDto contactFormSubmitDto) {
        ContactForm savedForm = service.saveContactForm(contactFormSubmitDto.toContactForm());
        return ResponseEntity.ok(savedForm);
    }
}
