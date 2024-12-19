package com.example.regionaldelicacy.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.regionaldelicacy.dtos.ProductDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

public class CustomProductRepositoryImpl implements CustomProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ProductDto> findProductList(Long userId, String searchTerm, String categoryName, Pageable pageable) {
        StringBuilder jpql = new StringBuilder("""
            SELECT new com.example.regionaldelicacy.dtos.ProductDto(
                p.id,
                p.name,
                p.description,
                p.price,
                p.imageUrl,
                c.name,
                p.brand,
                p.stock,
                f.id
            )
            FROM Product p
            JOIN p.category c
            LEFT JOIN (
                SELECT
                    f.id AS id,
                    f.product.id AS product_id
                FROM Favorite f
                WHERE f.user.id = :userId
            ) f ON p.id = f.product_id
        """);
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(p) FROM Product p JOIN p.category c");
        Boolean isEmptySearchTerm = searchTerm == null || searchTerm.trim().isEmpty();
        Boolean isEmptyCategoryName = categoryName == null || categoryName.trim().isEmpty();

        List<String> conditions = new ArrayList<>();
        // Optional searching by product name
        if (!isEmptySearchTerm) {
            conditions.add("LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))");
        }
        // Optional filtering by category name
        if (!isEmptyCategoryName) {
            conditions.add("LOWER(c.name) = LOWER(:categoryName)");
        }
        if (!conditions.isEmpty()) {
            String whereClause = " WHERE " + String.join(" AND ", conditions);
            jpql.append(whereClause);
            countJpql.append(whereClause);
        }

        // Sorting with pageable sort
        if (pageable.getSort().isSorted()) {
            jpql.append(" ORDER BY ");
            jpql.append(pageable.getSort().stream()
                    .map(order -> String.format("p.%s %s", order.getProperty(), order.getDirection()))
                    .reduce((s1, s2) -> s1 + ", " + s2)
                    .orElse(""));
        }

        // Create the query
        TypedQuery<ProductDto> query = entityManager.createQuery(jpql.toString(), ProductDto.class);
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql.toString(), Long.class);

        // Set parameters
        query.setParameter("userId", userId);
        if (!isEmptySearchTerm) {
            query.setParameter("searchTerm", searchTerm);
            countQuery.setParameter("searchTerm", searchTerm);
        }
        if (!isEmptyCategoryName) {
            query.setParameter("categoryName", categoryName);
            countQuery.setParameter("categoryName", categoryName);
        }

        // Pagination
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        // Execute the query
        List<ProductDto> productDtos = query.getResultList();
        Long totalElements = countQuery.getSingleResult();

        return new PageImpl<>(productDtos, pageable, totalElements);
    }
}
