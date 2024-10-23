package com.example.regionaldelicacy.services;

import com.example.regionaldelicacy.exceptions.ProductNotFoundException;
import com.example.regionaldelicacy.models.Product;
import com.example.regionaldelicacy.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        List<Product> product = productRepository.findByProductId(id);
        if (product == null || product.size() == 0) {
            throw new ProductNotFoundException();
        }
        return product.get(0);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
