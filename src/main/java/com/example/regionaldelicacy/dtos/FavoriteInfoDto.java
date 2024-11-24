package com.example.regionaldelicacy.dtos;

import java.time.Instant;

import com.example.regionaldelicacy.models.Favorite;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteInfoDto {
    private Long favoriteId;
    private Instant createdAt;
    private ProductDto productInfo;

    public static FavoriteInfoDto fromFavorite(Favorite favorite) {
        return new FavoriteInfoDto(
                favorite.getId(),
                favorite.getCreatedAt(),
                ProductDto.fromProduct(favorite.getProduct()));
    }
}
