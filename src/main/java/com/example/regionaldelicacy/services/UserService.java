package com.example.regionaldelicacy.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.regionaldelicacy.dtos.UpdatePasswordDto;
import com.example.regionaldelicacy.dtos.UpdateUserDetailsDto;
import com.example.regionaldelicacy.exceptions.DuplicateEmailException;
import com.example.regionaldelicacy.exceptions.InvalidPasswordException;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public User updateUserDetails(User currentUser, UpdateUserDetailsDto updateDto) {
        if (!updateDto.getEmail().equals(currentUser.getEmail()) &&
                userRepository.findByEmail(updateDto.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }

        currentUser.setName(updateDto.getName());
        currentUser.setEmail(updateDto.getEmail());
        currentUser.setPhoneNumber(updateDto.getPhoneNumber());
        currentUser.setAddress(updateDto.getAddress());
        currentUser.setCity(updateDto.getCity());
        currentUser.setCountry(updateDto.getCountry());

        return userRepository.save(currentUser);
    }

    public User changePassword(User currentUser, UpdatePasswordDto updateDto) {
        if (!passwordEncoder.matches(updateDto.getCurrentPassword(), currentUser.getPassword())) {
            throw new InvalidPasswordException("Current password is incorrect");
        }

        if (!updateDto.getNewPassword().equals(updateDto.getConfirmPassword())) {
            throw new InvalidPasswordException("New password and confirm password do not match");
        }

        currentUser.setPassword(passwordEncoder.encode(updateDto.getNewPassword()));
        return userRepository.save(currentUser);
    }
}
