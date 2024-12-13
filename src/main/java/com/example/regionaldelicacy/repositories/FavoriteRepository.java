package com.example.regionaldelicacy.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.regionaldelicacy.models.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Page<Favorite> findByUserIdOrderByUpdatedAtDesc(Long userId, Pageable page);
    Optional<Favorite> findByUserIdAndProductId(Long userId, Long productId);
    Boolean existsByUserIdAndProductId(Long userId, Long productId);
    Optional<Favorite> findByIdAndUserId(Long id, Long userId);
}
