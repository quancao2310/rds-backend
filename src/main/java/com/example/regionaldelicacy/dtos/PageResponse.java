package com.example.regionaldelicacy.dtos;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PageResponse<T> {
    private long totalElements;
    private int totalPages;
    private List<T> content;

    public static <T> PageResponse<T> of(Page<T> page) {
        PageResponse<T> response = new PageResponse<>();
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setContent(page.getContent());
        return response;
    }
}
