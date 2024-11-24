package com.example.regionaldelicacy.security;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.example.regionaldelicacy.exceptions.InvalidJwtException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ApplicationContext applicationContext;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("{}: {}", authException.getClass().getName(), authException.getMessage());
        HandlerExceptionResolver resolver = applicationContext.getBean("handlerExceptionResolver", HandlerExceptionResolver.class);
        resolver.resolveException(request, response, null, new InvalidJwtException());
    }
}
