package com.example.regionaldelicacy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.regionaldelicacy.dto.CreateProductDto;
import com.example.regionaldelicacy.exceptions.CategoryNotFoundException;
import com.example.regionaldelicacy.exceptions.ProductNotFoundException;
import com.example.regionaldelicacy.models.Product;
import com.example.regionaldelicacy.repositories.CategoryRepository;
import com.example.regionaldelicacy.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String categoryParam) {
        try {
            Long categoryId = Long.parseLong(categoryParam);
            return productRepository.findByCategoryCategoryId(categoryId);
        } catch (NumberFormatException e) {
            return productRepository.findByCategoryNameIgnoreCase(categoryParam);
        }
    }

    public List<Product> searchProducts(String searchTerm, String categoryParam) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return categoryParam == null ? getAllProducts() : getProductsByCategory(categoryParam);
        }

        String trimmedSearchTerm = searchTerm.trim();

        if (categoryParam == null) {
            return productRepository.findByNameContainingIgnoreCaseAndAccent(trimmedSearchTerm);
        }

        try {
            Long categoryId = Long.parseLong(categoryParam);
            return productRepository.findByNameContainingIgnoreCaseAndAccentAndCategoryCategoryId(trimmedSearchTerm, categoryId);
        } catch (NumberFormatException e) {
            return productRepository.findByNameContainingIgnoreCaseAndAccentAndCategoryNameIgnoreCase(trimmedSearchTerm, categoryParam);
        }
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public Product saveProduct(CreateProductDto productDto) {
        Product product = productDto.toProduct();
        product.setCategory(categoryRepository.findById(productDto.categoryId())
            .orElseThrow(CategoryNotFoundException::new));
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
