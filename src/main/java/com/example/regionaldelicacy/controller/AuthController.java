package com.example.regionaldelicacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dto.JwtDto;
import com.example.regionaldelicacy.dto.SignInDto;
import com.example.regionaldelicacy.dto.SignUpDto;
import com.example.regionaldelicacy.exceptions.DuplicateEmailException;
import com.example.regionaldelicacy.exceptions.ErrorMessageForException;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.security.TokenProvider;
import com.example.regionaldelicacy.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/auth")
@Tag(name = "Sign In and Sign Up", description = "Sign In and Sign Up API")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;
    @Autowired
    private TokenProvider tokenProvider;

    @Operation(summary = "Register a new user", description = "API to submit info to register a new user")
    @ApiResponse(responseCode = "201", description = "A new user has been successfully created")
    @ApiResponse(responseCode = "409", description = "New user can not be created because the email was used", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessageForException.class)) })
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDto data) throws DuplicateEmailException {
        authService.signUp(data);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Sign In to the system", description = "API to sign in and get JWT token")
    @ApiResponse(responseCode = "200", description = "Sign In successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = JwtDto.class)) })
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authUser = authenticationManager.authenticate(usernamePassword);
        var accessToken = tokenProvider.generateAccessToken((User) authUser.getPrincipal());
        return ResponseEntity.ok(new JwtDto(accessToken));
    }
}
