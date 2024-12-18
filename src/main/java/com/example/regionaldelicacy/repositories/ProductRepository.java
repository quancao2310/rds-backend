package com.example.regionaldelicacy.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.regionaldelicacy.dtos.ProductDto;
import com.example.regionaldelicacy.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, CustomProductRepository {
    @Query("""
        SELECT new com.example.regionaldelicacy.dtos.ProductDto(
            p.id,
            p.name,
            p.description,
            p.price,
            p.imageUrl,
            c.name,
            p.brand,
            p.stock,
            f.id
        )
        FROM Product p
        JOIN p.category c
        LEFT JOIN (
            SELECT
                f.id AS id,
                f.product.id AS product_id
            FROM Favorite f
            WHERE f.user.id = :userId
        ) f ON p.id = f.product_id
        WHERE p.id = :id
    """)
    Optional<ProductDto> findByIdWithFavoriteId(@Param("id") Long id, @Param("userId") Long userId);
}
