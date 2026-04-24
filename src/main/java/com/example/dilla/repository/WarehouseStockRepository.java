package com.example.dilla.repository;

import com.example.dilla.model.WarehouseStock;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {

    Optional<WarehouseStock> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    @Query("""
        SELECT ws FROM WarehouseStock ws
        WHERE ws.stock < 10
        AND ws.product.isActive = true
""")
    List<WarehouseStock> findLowStock();

    @Query("""
        SELECT SUM (ws.stock) FROM WarehouseStock ws
        WHERE ws.product.skuCode = :sku
        AND ws.product.isActive = true
""")
    Integer getTotalStock(String sku);

@Modifying
@Transactional
@Query("DELETE FROM WarehouseStock ws WHERE ws.product.id = :productId")
void deleteByProductId(@Param("productId") Long productId);

}
