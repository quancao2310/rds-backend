package com.example.regionaldelicacy.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.regionaldelicacy.models.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Page<Favorite> findByUserUserIdOrderByUpdatedAtDesc(Long userId, Pageable page);
    Optional<Favorite> findByUserUserIdAndProductProductId(Long userId, Long productId);
    Boolean existsByUserUserIdAndProductProductId(Long userId, Long productId);
    Optional<Favorite> findByIdAndUserUserId(Long id, Long userId);
}
