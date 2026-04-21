    package com.example.dilla.service;

    import com.example.dilla.exception.NotFoundException;
    import com.example.dilla.model.*;
    import com.example.dilla.repository.*;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.time.LocalDateTime;

    @Service
    public class StockService {

        private final ProductRepository productRepository;
        private final WarehouseRepository warehouseRepository;
        private final WarehouseStockRepository stockRepository;
        private final StockMutationRepository mutationRepository;

        public StockService(ProductRepository productRepository,
                            WarehouseRepository warehouseRepository,
                            WarehouseStockRepository stockRepository,
                            StockMutationRepository mutationRepository) {
            this.productRepository = productRepository;
            this.warehouseRepository = warehouseRepository;
            this.stockRepository = stockRepository;
            this.mutationRepository = mutationRepository;
        }

        // stock
        @Transactional
        public void stockIn(String sku, Long warehouseId, Integer qty) {

            if (qty <= 0) {
                throw new RuntimeException("Quantity harus lebih dari 0");
            }

            Product product = productRepository.findBySkuCodeAndIsActiveTrue(sku)
                    .orElseThrow(() -> new NotFoundException("Product tidak ditemukan / non aktif"));

            Warehouse warehouse = warehouseRepository.findById(warehouseId)
                    .orElseThrow(() -> new NotFoundException("Warehouse tidak ditemukan"));

            WarehouseStock stock = stockRepository
                    .findByProductIdAndWarehouseId(product.getId(), warehouseId)
                    .orElse(new WarehouseStock());

            stock.setProduct(product);
            stock.setWarehouse(warehouse);
            stock.setStock((stock.getStock() == null ? 0 : stock.getStock()) + qty);

            stockRepository.save(stock);

            // log mutation
            StockMutation mutation = new StockMutation();
            mutation.setProduct(product);
            mutation.setToWarehouse(warehouse);
            mutation.setQuantity(qty);
            mutation.setType("IN");
            mutation.setTimestamp(LocalDateTime.now());

            mutationRepository.save(mutation);
        }

        // transfer
        @Transactional
        public void transfer(String sku, Long fromId, Long toId, Integer qty) {

            if (qty <= 0) {
                throw new RuntimeException("Quantity harus lebih dari 0");
            }

            Product product = productRepository.findBySkuCodeAndIsActiveTrue(sku)
                    .orElseThrow(() -> new NotFoundException("Product tidak ditemukan / non aktif"));

            // stok asal
            WarehouseStock fromStock = stockRepository
                    .findByProductIdAndWarehouseId(product.getId(), fromId)
                    .orElseThrow(() -> new NotFoundException("Stok gudang asal tidak ditemukan"));

            if (fromStock.getStock() < qty) {
                throw new RuntimeException("Stok tidak mencukupi");
            }

            // kurangi stok asal
            fromStock.setStock(fromStock.getStock() - qty);
            stockRepository.save(fromStock);

            // stok tujuan
            Warehouse warehouseTo = warehouseRepository.findById(toId)
                    .orElseThrow(() -> new NotFoundException("Warehouse tujuan tidak ditemukan"));

            WarehouseStock toStock = stockRepository
                    .findByProductIdAndWarehouseId(product.getId(), toId)
                    .orElse(new WarehouseStock());

            toStock.setProduct(product);
            toStock.setWarehouse(warehouseTo);
            toStock.setStock((toStock.getStock() == null ? 0 : toStock.getStock()) + qty);

            stockRepository.save(toStock);

            // log mutation
            StockMutation mutation = new StockMutation();
            mutation.setProduct(product);
            mutation.setFromWarehouse(fromStock.getWarehouse());
            mutation.setToWarehouse(warehouseTo);
            mutation.setQuantity(qty);
            mutation.setType("TRANSFER");
            mutation.setTimestamp(LocalDateTime.now());

            mutationRepository.save(mutation);
        }
    }