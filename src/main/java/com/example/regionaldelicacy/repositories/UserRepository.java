package com.example.regionaldelicacy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.regionaldelicacy.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(Long userId);

    User findByName(String userName);

    User findByEmail(String email);
}
