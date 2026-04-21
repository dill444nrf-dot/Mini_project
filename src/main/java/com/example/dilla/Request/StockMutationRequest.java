package com.example.dilla.Request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StockMutationRequest {


    @NotBlank(message = "SKU tidak boleh kosong")
    private String skuCode;

    private Long warehouseId;

    private Long fromWarehouseId;
    private Long toWarehouseId;

    @Min(value = 1, message = "Quantity tidak boleh 0 atau negatif")
    private Integer quantity;

    private String type;

}
