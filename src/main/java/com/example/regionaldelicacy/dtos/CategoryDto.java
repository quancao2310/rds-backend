package com.example.regionaldelicacy.dtos;

import java.time.Instant;

import com.example.regionaldelicacy.models.Category;

import lombok.Data;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private Instant createdAt;
    private Instant updatedAt;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }
}
