package com.example.regionaldelicacy.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.regionaldelicacy.models.Category;
import com.example.regionaldelicacy.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
