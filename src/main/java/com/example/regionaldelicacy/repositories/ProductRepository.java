package com.example.regionaldelicacy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.regionaldelicacy.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryCategoryId(Long categoryId);
    List<Product> findByCategoryNameIgnoreCase(String categoryName);
    Product findByProductId(Long productId);

    // Search by name
    @Query(value = """
            SELECT p.* FROM product p
            WHERE LOWER(unaccent(p.name)) LIKE LOWER(unaccent(CONCAT('%', :name, '%')))
            """, nativeQuery = true)
    List<Product> findByNameContainingIgnoreCaseAndAccent(String name);

    @Query(value = """
            SELECT p.* FROM product p 
            WHERE LOWER(unaccent(p.name)) LIKE LOWER(unaccent(CONCAT('%', :name, '%'))) 
            AND p.category_id = :categoryId
            """, nativeQuery = true)
    List<Product> findByNameContainingIgnoreCaseAndAccentAndCategoryCategoryId(String name, Long categoryId);

    @Query(value = """
            SELECT p.* FROM product p 
            JOIN category c ON p.category_id = c.category_id 
            WHERE LOWER(unaccent(p.name)) LIKE LOWER(unaccent(CONCAT('%', :name, '%'))) 
            AND LOWER(c.name) = LOWER(:categoryName)
            """, nativeQuery = true)
    List<Product> findByNameContainingIgnoreCaseAndAccentAndCategoryNameIgnoreCase(String name, String categoryName);
}
