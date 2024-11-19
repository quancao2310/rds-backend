package com.example.regionaldelicacy.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.regionaldelicacy.models.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserUserId(Long userId, Pageable page);
    Favorite findByUserUserIdAndProductProductId(Long userId, Long productId);
}
