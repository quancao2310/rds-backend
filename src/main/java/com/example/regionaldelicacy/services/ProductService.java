package com.example.regionaldelicacy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.regionaldelicacy.exceptions.ProductNotFoundException;
import com.example.regionaldelicacy.models.Product;
import com.example.regionaldelicacy.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(ProductNotFoundException::new);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
