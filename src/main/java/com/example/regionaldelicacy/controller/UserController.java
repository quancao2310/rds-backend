package com.example.regionaldelicacy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dto.UserDetailsDto;
import com.example.regionaldelicacy.models.User;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/api/v1/users")
@Tag(name = "User", description = "APIs for users")
public class UserController {
    

    @GetMapping("/me")
    public ResponseEntity<UserDetailsDto> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserDetailsDto(user));
    }
}
