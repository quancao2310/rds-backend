package com.example.regionaldelicacy.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.regionaldelicacy.dtos.FavoriteInfoDto;
import com.example.regionaldelicacy.dtos.ProductCreateUpdateDto;
import com.example.regionaldelicacy.dtos.ProductDto;
import com.example.regionaldelicacy.exceptions.CategoryNotFoundException;
import com.example.regionaldelicacy.exceptions.ProductNotFoundException;
import com.example.regionaldelicacy.models.Category;
import com.example.regionaldelicacy.models.Favorite;
import com.example.regionaldelicacy.models.Product;
import com.example.regionaldelicacy.models.User;
import com.example.regionaldelicacy.repositories.CategoryRepository;
import com.example.regionaldelicacy.repositories.FavoriteRepository;
import com.example.regionaldelicacy.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FavoriteRepository favoriteRepository;

    private Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    private Page<Product> getProductsByCategory(String categoryParam, Pageable pageable) {
        try {
            Long categoryId = Long.parseLong(categoryParam);
            return productRepository.findByCategoryId(categoryId, pageable);
        } catch (NumberFormatException e) {
            return productRepository.findByCategoryNameIgnoreCase(categoryParam, pageable);
        }
    }

    public Page<ProductDto> searchProducts(String searchTerm, String categoryParam, Pageable pageable) {
        Page<Product> productPage = null;

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            productPage = categoryParam == null ? getAllProducts(pageable) : getProductsByCategory(categoryParam, pageable);
        } else if (categoryParam == null) {
            productPage = productRepository.findByNameContainingIgnoreCase(searchTerm.trim(), pageable);
        } else {
            try {
                Long categoryId = Long.parseLong(categoryParam);
                productPage = productRepository.findByNameContainingIgnoreCaseAndCategoryId(
                    searchTerm.trim(),
                    categoryId,
                    pageable
                );
            } catch (NumberFormatException e) {
                productPage = productRepository.findByNameContainingIgnoreCaseAndCategoryNameIgnoreCase(
                    searchTerm.trim(),
                    categoryParam,
                    pageable
                );
            }
        }

        // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
        //     User user = (User) auth.getPrincipal();
        //     return productPage.map(product -> new ProductDto(product, favoriteRepository
        //             .existsByUserUserIdAndProductProductId(user.getUserId(), product.getProductId()))); // N + 1 problem
        // }
        return productPage.map(ProductDto::fromProduct);
    }

    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            return new ProductDto(product, favoriteRepository
                    .existsByUserUserIdAndProductProductId(user.getUserId(), id));
        }
        return ProductDto.fromProduct(product);
    }

    public Product createProduct(ProductCreateUpdateDto productCreateDto) {
        Product product = productCreateDto.toProduct();
        product.setCategory(categoryRepository.findById(productCreateDto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new));
        return productRepository.save(product);
    }

    public Product updateProductById(Long id, ProductCreateUpdateDto productUpdateDto) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        Category category = categoryRepository.findById(productUpdateDto.getCategoryId())
                .orElseThrow(CategoryNotFoundException::new);

        product.setName(productUpdateDto.getName());
        product.setDescription(productUpdateDto.getDescription());
        product.setPrice(productUpdateDto.getPrice());
        product.setImageUrl(productUpdateDto.getImageUrl());
        product.setBrand(productUpdateDto.getBrand());
        product.setStock(productUpdateDto.getStock());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<FavoriteInfoDto> getFavoriteProducts(User user, Pageable pageable) {
        Page<Favorite> favoriteProducts = favoriteRepository.findByUserUserIdOrderByUpdatedAtDesc(user.getUserId(), pageable);
        Page<FavoriteInfoDto> favoriteDtos = favoriteProducts.map(FavoriteInfoDto::fromFavorite);
        return favoriteDtos;
    }

    public FavoriteInfoDto addFavoriteProduct(User user, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        Favorite favorite = favoriteRepository.findByUserUserIdAndProductProductId(user.getUserId(), productId).orElse(null);

        if (favorite == null) {
            favorite = Favorite.builder()
                    .user(user)
                    .product(product)
                    .build();
            favorite = favoriteRepository.save(favorite);
        }
        return FavoriteInfoDto.fromFavorite(favorite);
    }

    public void removeFavoriteProduct(User user, Long favoriteId) {
        if (favoriteRepository.findByIdAndUserUserId(favoriteId, user.getUserId()).isPresent()) {
            favoriteRepository.deleteById(favoriteId);
        }
    }
}
