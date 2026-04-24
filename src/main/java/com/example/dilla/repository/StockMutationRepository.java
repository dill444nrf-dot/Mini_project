package com.example.dilla.repository;

import com.example.dilla.model.StockMutation;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockMutationRepository extends JpaRepository<StockMutation, Long> {

    // agar null saat product dihapus
    @Modifying
    @Transactional
    @Query("UPDATE StockMutation sm SET sm.product = null WHERE sm.product.id = :productId")
    void nullifyProductId(Long productId);

    // 🔥 QUERY UTAMA (SESUAI TUGAS)
    @Query("""
        SELECT sm FROM StockMutation sm
        JOIN sm.product p
        WHERE p.isActive = true
        AND (
            :type = 'ALL' OR
            (:type = 'IN' AND sm.type = 'IN') OR
            (:type = 'OUT' AND sm.type IN ('OUT', 'TRANSFER'))
        )
        ORDER BY sm.timestamp DESC
    """)
    List<StockMutation> findLatestMutation(
            @Param("type") String type,
            Pageable pageable
    );
}