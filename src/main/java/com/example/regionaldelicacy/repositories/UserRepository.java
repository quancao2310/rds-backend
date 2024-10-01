package com.example.regionaldelicacy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.regionaldelicacy.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByUserId(Long userId);
    UserDetails findByName(String userName);
    UserDetails findByEmail(String email);
}
