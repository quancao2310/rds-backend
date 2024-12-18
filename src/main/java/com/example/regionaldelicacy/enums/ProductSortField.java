package com.example.regionaldelicacy.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSortField {
    NAME("name"),
    PRICE("price");

    private final String fieldName;

    public static ProductSortField fromString(String value) throws IllegalArgumentException {
        for (ProductSortField field : values()) {
            if (field.getFieldName().equals(value)) {
                return field;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid sort field: '%s'", value));
    }
}
