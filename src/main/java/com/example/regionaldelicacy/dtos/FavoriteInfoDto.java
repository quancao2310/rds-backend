package com.example.regionaldelicacy.dtos;

import java.time.Instant;

import com.example.regionaldelicacy.models.Favorite;
import com.example.regionaldelicacy.serializers.InstantSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FavoriteInfoDto {
    private Long favoriteId;
    @JsonSerialize(using = InstantSerializer.class)
    private Instant createdAt;
    private ProductDto productInfo;

    public static FavoriteInfoDto fromFavorite(Favorite favorite) {
        return new FavoriteInfoDto(
                favorite.getId(),
                favorite.getCreatedAt(),
                ProductDto.fromProduct(favorite.getProduct()));
    }
}
