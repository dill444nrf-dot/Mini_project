package com.example.dilla.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank(message = "SKU tidak boleh kosong")
    private String skuCode;

    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @NotNull(message = "Harga tidak boleh kosong")
    @Min(value = 1000, message = "Harga minimal 1.000")
    private Double price;

    private Long categoryId;
}