package com.example.regionaldelicacy.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.regionaldelicacy.models.OrderInfo;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {
    List<OrderInfo> findByUserId(Long userId, Pageable paging);
    OrderInfo findByIdAndUserId(Long orderId, Long userId);
}
