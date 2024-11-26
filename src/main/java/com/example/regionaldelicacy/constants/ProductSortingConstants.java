package com.example.regionaldelicacy.constants;

import java.util.Arrays;
import java.util.List;

import com.example.regionaldelicacy.enums.ProductSortField;

public class ProductSortingConstants {
    private ProductSortingConstants() {}

    public static final String DEFAULT_SORT_BY = ProductSortField.UPDATED_AT.getFieldName();
    public static final String DEFAULT_SORT_ORDER = "desc";
    
    public static final List<String> ALLOWED_SORT_FIELDS = Arrays.stream(ProductSortField.values())
            .map(ProductSortField::getFieldName)
            .toList();

    public static String getAllowedSortFieldsString() {
        return String.join(", ", ALLOWED_SORT_FIELDS);
    }
}
