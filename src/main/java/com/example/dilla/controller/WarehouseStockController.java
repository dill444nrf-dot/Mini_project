package com.example.dilla.controller;

import com.example.dilla.model.WarehouseStock;
import com.example.dilla.response.WebResponse;
import com.example.dilla.repository.WarehouseStockRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouse-stocks")
public class WarehouseStockController {

    private final WarehouseStockRepository repository;

    public WarehouseStockController(WarehouseStockRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public WebResponse<List<WarehouseStock>> getAll() {
        return WebResponse.<List<WarehouseStock>>builder()
                .status("Success")
                .message("Data stok berhasil diambil")
                .data(repository.findAll())
                .build();
    }
}