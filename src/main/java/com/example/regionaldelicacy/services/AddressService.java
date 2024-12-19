package com.example.regionaldelicacy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.regionaldelicacy.dtos.AddressCreateUpdateDto;
import com.example.regionaldelicacy.dtos.AddressDto;
import com.example.regionaldelicacy.exceptions.AddressNotFoundException;
import com.example.regionaldelicacy.models.Address;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.repositories.AddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressDto createAddress(AddressCreateUpdateDto addressCreateDto, User user) {
        Address address = addressCreateDto.toAddress();
        address.setUser(user);
        return new AddressDto(addressRepository.save(address));
    }

    public List<AddressDto> getAddressesByUserId(Long userId) {
        List<Address> addresses = addressRepository.findByUserId(userId);
        return addresses.stream()
                .map(AddressDto::new)
                .toList();
    }

    public AddressDto getAddressById(Long id, User user) {
        Address address = addressRepository.findById(id).orElseThrow(AddressNotFoundException::new);
        if (!isAddressBelongsToUser(address, user)) {
            throw new AddressNotFoundException();
        }
        return new AddressDto(address);
    }

    public AddressDto updateAddress(Long id, AddressCreateUpdateDto addressUpdateDto, User user) {
        Address address = addressRepository.findById(id).orElseThrow(AddressNotFoundException::new);
        if (!isAddressBelongsToUser(address, user)) {
            throw new AddressNotFoundException();
        }

        address.setAddress(addressUpdateDto.getAddress());
        address.setCity(addressUpdateDto.getCity());
        address.setCountry(addressUpdateDto.getCountry());
        address.setPostalCode(addressUpdateDto.getPostalCode());
        address.setPhoneNumber(addressUpdateDto.getPhoneNumber());
        address.setCustomerName(addressUpdateDto.getCustomerName());

        return new AddressDto(addressRepository.save(address));
    }

    public void deleteAddress(Long id, User user) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent() && isAddressBelongsToUser(address.get(), user)) {
            addressRepository.deleteById(id);
        }
    }

    private boolean isAddressBelongsToUser(Address address, User user) {
        return address.getUser().getId().equals(user.getId());
    }
}
