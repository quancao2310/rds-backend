package com.example.regionaldelicacy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.regionaldelicacy.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryCategoryId(Long categoryId);
    List<Product> findByCategoryNameIgnoreCase(String categoryName);

    // Search by name
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByNameContainingIgnoreCaseAndCategoryCategoryId(String name, Long categoryId);
    List<Product> findByNameContainingIgnoreCaseAndCategoryNameIgnoreCase(String name, String categoryName);
}
