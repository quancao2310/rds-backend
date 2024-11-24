package com.example.regionaldelicacy.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dtos.UpdatePasswordDto;
import com.example.regionaldelicacy.dtos.UpdateUserDetailsDto;
import com.example.regionaldelicacy.dtos.UserDetailsDto;
import com.example.regionaldelicacy.exceptions.ErrorResponse;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/api/v1/users")
@Tag(name = "User", description = "APIs for users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get user profile", description = "API to get user profile information")
    @ApiResponse(responseCode = "200", description = "Profile retrieved successfully")
    @ApiResponse(responseCode = "401", description = "User not authenticated", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @GetMapping("/profile")
    public ResponseEntity<UserDetailsDto> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserDetailsDto(user));
    }

    @Operation(summary = "Update user profile", description = "API to update user profile information")
    @ApiResponse(responseCode = "200", description = "Profile updated successfully")
    @ApiResponse(responseCode = "401", description = "User not authenticated", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Email already in use", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping("/profile")
    public ResponseEntity<UserDetailsDto> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UpdateUserDetailsDto updateDto) {
        User updatedUser = userService.updateUserDetails(user, updateDto);
        return ResponseEntity.ok(new UserDetailsDto(updatedUser));
    }

    @Operation(summary = "Change password", description = "API to change user password")
    @ApiResponse(responseCode = "200", description = "Password changed successfully")
    @ApiResponse(responseCode = "401", description = "User not authenticated", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid password data", 
        content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UpdatePasswordDto updateDto) {
        userService.changePassword(user, updateDto);
        return ResponseEntity.ok().build();
    }
}
