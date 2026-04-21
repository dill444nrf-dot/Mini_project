package com.example.dilla.controller;

import com.example.dilla.Request.StockMutationRequest;
import com.example.dilla.response.WebResponse;
import com.example.dilla.service.StockService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    // STOCK IN
    @PostMapping("/in")
    public WebResponse<String> stockIn(@RequestBody @Valid StockMutationRequest request) {

        service.stockIn(
                request.getSkuCode(),
                request.getWarehouseId(),
                request.getQuantity()
        );

        return WebResponse.<String>builder()
                .status("Success")
                .message("Stok berhasil ditambahkan")
                .data(null)
                .build();
    }

    // TRANSFER
    @PostMapping("/transfer")
    public WebResponse<String> transfer(@RequestBody @Valid StockMutationRequest request) {

        service.transfer(
                request.getSkuCode(),
                request.getFromWarehouseId(),
                request.getToWarehouseId(),
                request.getQuantity()
        );

        return WebResponse.<String>builder()
                .status("Success")
                .message("Transfer berhasil")
                .data(null)
                .build();
    }
}