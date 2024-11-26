package com.example.regionaldelicacy.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.regionaldelicacy.constants.ProductPagingConstants;
import com.example.regionaldelicacy.constants.ProductSortingConstants;
import com.example.regionaldelicacy.enums.ProductSortField;
import com.example.regionaldelicacy.exceptions.InvalidPaginationParametersException;

public final class ProductPaginationUtils {
    private ProductPaginationUtils() {}

    public static Pageable validateAndCreatePageable(int page, int size, String sortBy, String sortOrder) {
        if (page < ProductPagingConstants.MIN_PAGE_NUMBER) {
            throw new InvalidPaginationParametersException("Page number cannot be less than " + ProductPagingConstants.MIN_PAGE_NUMBER);
        }
        if (size < 1) {
            throw new InvalidPaginationParametersException("Page size cannot be less than 1");
        }
        if (size > ProductPagingConstants.MAX_PAGE_SIZE) {
            throw new InvalidPaginationParametersException("Page size cannot be greater than " + ProductPagingConstants.MAX_PAGE_SIZE);
        }

        ProductSortField sortField;
        try {
            sortField = ProductSortField.fromString(sortBy);
        } catch (IllegalArgumentException e) {
            throw new InvalidPaginationParametersException(
                String.format("Invalid sort field: '%s'. Allowed values are: %s", 
                    sortBy, ProductSortingConstants.getAllowedSortFieldsString())
            );
        }

        Sort.Direction sortDirection;
        try {
            sortDirection = Sort.Direction.fromString(sortOrder);
        } catch (IllegalArgumentException e) {
            throw new InvalidPaginationParametersException(e.getMessage());
        }

        return PageRequest.of(page - 1, size, Sort.by(sortDirection, sortField.getFieldName()));
    }
}
