package com.example.dilla.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class StockMutation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // dari warehouse
    @ManyToOne
    @JoinColumn(name = "from_warehouse_id")
    private Warehouse fromWarehouse;

    //ke warehouse
    @ManyToOne
    @JoinColumn(name = "to_warehouse_id")
    private Warehouse toWarehouse;

    private Integer quantity;

    // transfer ( in )
    private String type;

    private LocalDateTime timestamp;
}
