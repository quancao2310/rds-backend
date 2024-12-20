package com.example.regionaldelicacy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.regionaldelicacy.models.ContactForm;
import com.example.regionaldelicacy.repositories.ContactFormRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactFormService {

    @Value("${app.admin-email}")
    private String adminEmail;

    private final ContactFormRepository repository;
    private final SendgridService sendgridService;

    public ContactForm saveContactForm(ContactForm contactForm) {
        ContactForm savedContactForm =  repository.save(contactForm);
        sendEmailToAdmin(savedContactForm);
        return savedContactForm;
    }

    public List<ContactForm> getAllContactForms() {
        return repository.findAll();
    }

    private void sendEmailToAdmin(ContactForm contactForm) {
        String subject = "New Contact Form Submission";
        String content = "You have received a new message from " + contactForm.getName() +
                "\n\nEmail: " + contactForm.getEmail() +
                "\nPhone: " + contactForm.getPhone() +
                "\nMessage: " + contactForm.getMessage();
        sendgridService.sendEmail(adminEmail, subject, content);
    }
}
