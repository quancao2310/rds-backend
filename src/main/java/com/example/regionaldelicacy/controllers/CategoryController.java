package com.example.regionaldelicacy.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regionaldelicacy.dtos.CategoryCreateUpdateDto;
import com.example.regionaldelicacy.dtos.CategoryDto;
import com.example.regionaldelicacy.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "APIs for managing categories of products")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories")
    @ApiResponse(responseCode = "200", description = "List of categories retrieved successfully")
    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(summary = "Create a new category", description = "Create a new category with the provided information")
    @ApiResponse(responseCode = "201", description = "Category created")
    @PostMapping("")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryCreateUpdateDto categoryCreateDto) {
        CategoryDto categoryDto = categoryService.createCategory(categoryCreateDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a category by ID", description = "Retrieve a category by its ID")
    @ApiResponse(responseCode = "200", description = "Category retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Update a category by ID", description = "Update a category with the provided information")
    @ApiResponse(responseCode = "200", description = "Category updated successfully")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategoryById(@PathVariable Long id,
            @RequestBody @Valid CategoryCreateUpdateDto categoryUpdateDto) {
        return ResponseEntity.ok(categoryService.updateCategoryById(id, categoryUpdateDto));
    }

    @Operation(summary = "Delete a category by ID", description = "Delete a category by its ID")
    @ApiResponse(responseCode = "204", description = "Category deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
