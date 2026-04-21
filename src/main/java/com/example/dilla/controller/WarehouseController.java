package com.example.dilla.controller;

import com.example.dilla.model.Warehouse;
import com.example.dilla.response.WebResponse;
import com.example.dilla.service.WarehouseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final WarehouseService service;

    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @PostMapping
    public WebResponse<Warehouse> create(@RequestBody Warehouse warehouse) {
        return WebResponse.<Warehouse>builder()
                .status("Success")
                .data(service.create(warehouse))
                .build();
    }

    @GetMapping
    public WebResponse<List<Warehouse>> getAll() {
        return WebResponse.<List<Warehouse>>builder()
                .status("Success")
                .data(service.getAll())
                .build();
    }
}
