package com.example.dilla.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "warehouse_stocks", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "warehouse_id"}))
@Data
public class WarehouseStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //relasi ke product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    //relasi ke Warehouse
    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    private Integer stock;

    //Optimis locking
    @Version
    private Long version;
}
