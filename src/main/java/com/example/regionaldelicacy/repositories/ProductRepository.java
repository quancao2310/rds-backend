package com.example.regionaldelicacy.repositories;

import com.example.regionaldelicacy.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductId(Long productId);
}
