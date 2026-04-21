package com.example.dilla.service;

import com.example.dilla.exception.NotFoundException;
import com.example.dilla.model.Warehouse;
import com.example.dilla.repository.WarehouseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseRepository repository;

    public WarehouseService(WarehouseRepository repository) {
        this.repository = repository;
    }

    public Warehouse create(Warehouse warehouse) {
        return repository.save(warehouse);
    }

    public List<Warehouse> getAll() {
        return repository.findAll();
    }

    public Warehouse getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Warehouse tidak ditemukan"));
    }
}

