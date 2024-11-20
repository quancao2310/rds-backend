package com.example.regionaldelicacy.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dto.JwtResponseDto;
import com.example.regionaldelicacy.dto.SignInDto;
import com.example.regionaldelicacy.dto.SignUpDto;
import com.example.regionaldelicacy.exceptions.ErrorResponse;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.repositories.UserRepository;
import com.example.regionaldelicacy.security.JwtProvider;
import com.example.regionaldelicacy.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
@Tag(name = "Authentication", description = "APIs for authentication")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Operation(summary = "Register a new user", description = "API to submit info to register a new user")
    @ApiResponse(responseCode = "201", description = "A new user has been successfully created")
    @ApiResponse(responseCode = "409", description = "New user can not be created because the email was used", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)) })
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpDto signUpDto) {
        authService.signUp(signUpDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Sign In to the system", description = "API to sign in and get JWT token")
    @ApiResponse(responseCode = "200", description = "Sign In successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponseDto.class)) })
    @PostMapping("/signin")
    public ResponseEntity<JwtResponseDto> signIn(@RequestBody @Valid SignInDto signInDto) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword())
        );
        User user = (User) auth.getPrincipal();
        user.setLastLogin(Instant.now());
        userRepository.save(user);
        String accessToken = jwtProvider.generateAccessToken(user);
        return ResponseEntity.ok(new JwtResponseDto(accessToken));
    }
}
