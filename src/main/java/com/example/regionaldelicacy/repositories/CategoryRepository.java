package com.example.regionaldelicacy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.regionaldelicacy.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
