package com.example.regionaldelicacy.repositories;

import com.example.regionaldelicacy.models.Cart;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserUserIdAndDeletedFalseOrderByCreatedAtDesc(Long userId, Pageable page);

    Cart findByCartIdAndUserUserIdAndDeletedFalse(Long cartId, Long userId);

    Cart findByProductProductIdAndUserUserIdAndDeletedFalse(Long productId, Long userId);

    List<Cart> findAllByCartIdInAndDeletedFalse(List<Long> cartIds);
}
