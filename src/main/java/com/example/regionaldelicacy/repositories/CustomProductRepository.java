package com.example.regionaldelicacy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.regionaldelicacy.dtos.ProductDto;

public interface CustomProductRepository {
    Page<ProductDto> findProductList(Long userId, String searchTerm, String categoryParam, Pageable pageable);
}
