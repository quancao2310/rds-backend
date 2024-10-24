package com.example.regionaldelicacy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.regionaldelicacy.dto.SignUpDto;
import com.example.regionaldelicacy.enums.UserRole;
import com.example.regionaldelicacy.exceptions.DuplicateEmailException;
import com.example.regionaldelicacy.exceptions.UserNotFoundException;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.repositories.UserRepository;

@Service
public class AuthService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public UserDetails signUp(SignUpDto data) throws DuplicateEmailException {
        if (userRepository.findByEmail(data.email()) != null) {
            throw new DuplicateEmailException();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data, encryptedPassword, UserRole.USER);
        return userRepository.save(newUser);
    }
}
