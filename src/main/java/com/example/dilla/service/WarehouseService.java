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

    public Warehouse update(Long id, Warehouse request) {
        Warehouse warehouse = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Warehouse tidak ditemukan dengan id:" +id));

        if(repository.existsByCodeAndIdNot(request.getCode(),id)) {
            throw new IllegalArgumentException("kode gudang sudah digunakan:" + request.getCode());
        }

        warehouse.setCode(request.getCode());
        warehouse.setName(request.getName());

        return repository.save(warehouse);
    }


    public Warehouse getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Warehouse tidak ditemukan"));
    }
}

