package com.example.regionaldelicacy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.regionaldelicacy.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Product> findByCategoryNameIgnoreCase(String categoryName, Pageable pageable);
    Product findByProductId(Long productId);

    // Search by name
    @Query(value = """
            SELECT p.* FROM product p
            WHERE LOWER(unaccent(p.name)) LIKE LOWER(unaccent(CONCAT('%', :name, '%')))
            """,
            countQuery = """
            SELECT COUNT(*) FROM product p
            WHERE LOWER(unaccent(p.name)) LIKE LOWER(unaccent(CONCAT('%', :name, '%')))
            """,
            nativeQuery = true)
    Page<Product> findByNameContainingIgnoreCaseAndAccent(String name, Pageable pageable);

    @Query(value = """
            SELECT p.* FROM product p
            WHERE LOWER(unaccent(p.name)) LIKE LOWER(unaccent(CONCAT('%', :name, '%')))
            AND p.category_id = :categoryId
            """,
            countQuery = """
            SELECT COUNT(*) FROM product p 
            WHERE LOWER(unaccent(p.name)) LIKE LOWER(unaccent(CONCAT('%', :name, '%'))) 
            AND p.category_id = :categoryId
            """,
            nativeQuery = true)
    Page<Product> findByNameContainingIgnoreCaseAndAccentAndCategoryId(String name, Long categoryId, Pageable pageable);

    @Query(value = """
            SELECT p.* FROM product p
            JOIN category c ON p.category_id = c.id
            WHERE LOWER(unaccent(p.name)) LIKE LOWER(unaccent(CONCAT('%', :name, '%')))
            AND LOWER(c.name) = LOWER(:categoryName)
            """,
            countQuery = """
            SELECT COUNT(*) FROM product p
            JOIN category c ON p.category_id = c.id
            WHERE LOWER(unaccent(p.name)) LIKE LOWER(unaccent(CONCAT('%', :name, '%')))
            AND LOWER(c.name) = LOWER(:categoryName)
            """,
            nativeQuery = true)
    Page<Product> findByNameContainingIgnoreCaseAndAccentAndCategoryNameIgnoreCase(String name, String categoryName, Pageable pageable);
}
