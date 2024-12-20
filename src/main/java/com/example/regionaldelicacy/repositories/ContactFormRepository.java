package com.example.regionaldelicacy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.regionaldelicacy.models.ContactForm;

public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {
}
