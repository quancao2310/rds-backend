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

    public List<Product> getProductsByCategory(Long category) {
        return productRepository.findByCategoryCategoryId(category);
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
