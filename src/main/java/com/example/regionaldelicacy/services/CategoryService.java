package com.example.regionaldelicacy.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.regionaldelicacy.dtos.CategoryCreateUpdateDto;
import com.example.regionaldelicacy.dtos.CategoryDto;
import com.example.regionaldelicacy.exceptions.CategoryNotFoundException;
import com.example.regionaldelicacy.models.Category;
import com.example.regionaldelicacy.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategories() {
        List<Category> categories =  categoryRepository.findAll();
        return categories.stream()
                .map(CategoryDto::new)
                .toList();
    }

    public CategoryDto createCategory(CategoryCreateUpdateDto categoryCreateDto) {
        Category category = Category.builder()
                .name(categoryCreateDto.getName())
                .build();
        category = categoryRepository.save(category);
        return new CategoryDto(category);
    }

    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
        return new CategoryDto(category);
    }

    public CategoryDto updateCategoryById(Long id, CategoryCreateUpdateDto categoryUpdateDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
        category.setName(categoryUpdateDto.getName());
        category = categoryRepository.save(category);
        return new CategoryDto(category);
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
