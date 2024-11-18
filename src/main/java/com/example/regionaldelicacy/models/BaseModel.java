package com.example.regionaldelicacy.models;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.regionaldelicacy.serializers.InstantSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant updatedAt;
}
