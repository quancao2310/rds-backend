package com.example.regionaldelicacy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.Module;

@Configuration
public class JacksonConfig {

    @Bean
    Module javaTimeModule() {
        return new JavaTimeModule();
    }
}
