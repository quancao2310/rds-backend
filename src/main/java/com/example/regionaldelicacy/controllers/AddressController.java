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
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.services.AddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("")
    public ResponseEntity<AddressDto> createAddress(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid AddressCreateUpdateDto addressCreateDto) {
        AddressDto addressDto = addressService.createAddress(addressCreateDto, user);
        return new ResponseEntity<>(addressDto, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<AddressDto>> getUserAddresses(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(addressService.getAddressesByUserId(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(addressService.getAddressById(id, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(
            @PathVariable Long id,
            @RequestBody @Valid AddressCreateUpdateDto addressUpdateDto,
            @AuthenticationPrincipal User user) {
        AddressDto addressDto = addressService.updateAddress(id, addressUpdateDto, user);
        return ResponseEntity.ok(addressDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id, @AuthenticationPrincipal User user) {
        addressService.deleteAddress(id, user);
        return ResponseEntity.noContent().build();
    }
}
