package com.example.dilla.repository;

import com.example.dilla.model.StockMutation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StockMutationRepository extends JpaRepository<StockMutation, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE StockMutation sm SET sm.product = null WHERE sm.product.id = :productId")
    void nullifyProductId(Long productId);
}
