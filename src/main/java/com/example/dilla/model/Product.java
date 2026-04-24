package com.example.dilla.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isActive = true;

    @Column(name = "sku_code", unique = true, nullable = false)
    private String skuCode;

    @Column(nullable = false)
    private String name;

    private Double price;

    //relasi many to one ke category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    // Relasi one-to-many ke WarehouseStock
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true )
    private List<WarehouseStock> warehouseStocks;

    @JsonIgnore
    //relasi one-to-many stock mutation
    @OneToMany(mappedBy = "product")
    private List<StockMutation> stockMutations;

    //Optimis locking
    @Version
    private Long version;
}

