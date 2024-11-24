package com.example.regionaldelicacy.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.regionaldelicacy.dtos.SignUpDto;
import com.example.regionaldelicacy.enums.UserRole;
import com.example.regionaldelicacy.exceptions.DuplicateEmailException;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(SignUpDto data) {
        if (userRepository.findByEmail(data.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }

        String encryptedPassword = passwordEncoder.encode(data.getPassword());
        User newUser = new User(data, encryptedPassword, UserRole.USER);
        return userRepository.save(newUser);
    }
}
