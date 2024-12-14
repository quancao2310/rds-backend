package com.example.regionaldelicacy.repositories;

import com.example.regionaldelicacy.models.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserIdAndDeletedFalseOrderByCreatedAtDesc(Long userId);

    Optional<Cart> findByIdAndUserIdAndDeletedFalse(Long cartId, Long userId);

    Cart findByProductIdAndUserIdAndDeletedFalse(Long productId, Long userId);

    List<Cart> findAllByIdInAndDeletedFalse(List<Long> cartIds);
}
