package com.example.regionaldelicacy.repositories;

import com.example.regionaldelicacy.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserUserIdAndDeletedFalse(Long userId);

    List<Cart> findByCartIdAndUserUserIdAndDeletedFalse(Long cartId, Long userId);

    List<Cart> findAllByCartIdInAndDeletedFalse(List<Long> cartIds);
}
