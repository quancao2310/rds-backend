package com.example.regionaldelicacy.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.example.regionaldelicacy.utils.SecurityUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FavoriteRepository favoriteRepository;

    public Page<ProductDto> getProducts(String searchTerm, String categoryName, Pageable pageable) {
        return productRepository.findProductList(
            SecurityUtils.getCurrentUserId(),
            searchTerm,
            categoryName,
            pageable
        );
    }

    public ProductDto getProductById(Long id) {
        return productRepository
                .findByIdWithFavoriteId(id, SecurityUtils.getCurrentUserId())
                .orElseThrow(ProductNotFoundException::new);
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
        Page<Favorite> favoriteProducts = favoriteRepository.findByUserId(user.getId(), pageable);
        Page<FavoriteInfoDto> favoriteDtos = favoriteProducts.map(FavoriteInfoDto::fromFavorite);
        return favoriteDtos;
    }

    public FavoriteInfoDto addFavoriteProduct(User user, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        Favorite favorite = favoriteRepository.findByUserIdAndProductId(user.getId(), productId).orElse(null);

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
        if (favoriteRepository.findByIdAndUserId(favoriteId, user.getId()).isPresent()) {
            favoriteRepository.deleteById(favoriteId);
        }
    }
}
