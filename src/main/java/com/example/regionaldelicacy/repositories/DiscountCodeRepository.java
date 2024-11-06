package com.example.regionaldelicacy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.regionaldelicacy.models.DiscountCode;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long> {
    DiscountCode findByCode(String code);
}
