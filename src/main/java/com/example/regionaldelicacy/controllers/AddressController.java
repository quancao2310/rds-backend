package com.example.regionaldelicacy.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dtos.AddressCreateUpdateDto;
import com.example.regionaldelicacy.dtos.AddressDto;
import com.example.regionaldelicacy.exceptions.ErrorResponse;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.services.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Address", description = "API for managing user's addresses")
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Create a new address", description = "API to submit info to create a new address")
    @ApiResponse(responseCode = "201", description = "Address has been successfully created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class))
    })
    @ApiResponse(responseCode = "400", description = "Invalid input", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
    })
    @PostMapping("")
    public ResponseEntity<AddressDto> createAddress(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid AddressCreateUpdateDto addressCreateDto) {
        AddressDto addressDto = addressService.createAddress(addressCreateDto, user);
        return new ResponseEntity<>(addressDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all addresses of the authenticated user", description = "API to get all addresses of the authenticated user")
    @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AddressDto.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<AddressDto>> getUserAddresses(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(addressService.getAddressesByUserId(user.getId()));
    }

    @Operation(summary = "Get an address by id", description = "API to get an address by id")
    @ApiResponse(responseCode = "200", description = "Address retrieved successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(addressService.getAddressById(id, user));
    }

    @Operation(summary = "Update an address", description = "API to update an address")
    @ApiResponse(responseCode = "200", description = "Address updated successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(
            @PathVariable Long id,
            @RequestBody @Valid AddressCreateUpdateDto addressUpdateDto,
            @AuthenticationPrincipal User user) {
        AddressDto addressDto = addressService.updateAddress(id, addressUpdateDto, user);
        return ResponseEntity.ok(addressDto);
    }

    @Operation(summary = "Delete an address", description = "API to delete an address")
    @ApiResponse(responseCode = "204", description = "Address deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id, @AuthenticationPrincipal User user) {
        addressService.deleteAddress(id, user);
        return ResponseEntity.noContent().build();
    }
}
