package com.example.regionaldelicacy.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.regionaldelicacy.exceptions.InvalidPaginationParametersException;

public final class PaginationUtils {
    private PaginationUtils() {}

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;
    public static final int MIN_PAGE_NUMBER = 1;

    public static Pageable validateAndCreatePageable(int page, int size) {
        if (page < MIN_PAGE_NUMBER) {
            throw new InvalidPaginationParametersException("Page number cannot be less than " + MIN_PAGE_NUMBER);
        }
        if (size < 1) {
            throw new InvalidPaginationParametersException("Page size cannot be less than 1");
        }
        if (size > MAX_PAGE_SIZE) {
            throw new InvalidPaginationParametersException("Page size cannot be greater than " + MAX_PAGE_SIZE);
        }

        return PageRequest.of(page - 1, size);
    }
}
