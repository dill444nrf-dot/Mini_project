package com.example.dilla.controller;

import com.example.dilla.Request.ProductRequest;
import com.example.dilla.model.Product;
import com.example.dilla.response.WebResponse;
import com.example.dilla.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // create
    @PostMapping
    public WebResponse<Product> create(@RequestBody @Valid ProductRequest Request) {
        try {
            return WebResponse.<Product>builder()
                    .status("Success")
                    .data(service.create(Request))
                    .build();
        } catch (RuntimeException e) {
            return WebResponse.<Product>builder()
                    .status("failed")
                    .message(e.getMessage())
                    .data(null)
                    .build();
        }

    }

    //Read all
    @GetMapping
    public List<Product> semuaProduk() {
        return service.semuaProduk();
    }

    // update
    @PutMapping("/{id}")
    public WebResponse<Product> update(@PathVariable Long id, @RequestBody Product request) {
        return WebResponse.<Product>builder()
                .status("Success")
                .data(service.update(id, request))
                .build();
    }

    // soft delete
    @PutMapping("/delete/{id}")
    public WebResponse<String> delete(@PathVariable Long id) {
        service.softDelete(id);
        return WebResponse.<String>builder()
                .status("Success")
                .message("Product dinonaktifkan")
                .build();
    }

    // restore
    @PutMapping("/restore/{id}")
    public WebResponse<String> restore(@PathVariable Long id) {
        service.restore(id);
        return WebResponse.<String>builder()
                .status("Success")
                .message("Product diaktifkan kembali")
                .build();
    }

    // low stock
    @GetMapping("/low-stock")
    public WebResponse<?> lowStock() {
        return WebResponse.builder()
                .status("Success")
                .data(service.getLowStock())
                .build();
    }

    // stock summary
    @GetMapping("/{sku}/stock-summary")
    public WebResponse<Integer> summary(@PathVariable String sku) {
        return WebResponse.<Integer>builder()
                .status("Success")
                .message("Total stok berhasil diambil")
                .data(service.getStockSummary(sku))
                .build();
    }
}