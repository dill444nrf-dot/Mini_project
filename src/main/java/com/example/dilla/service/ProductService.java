package com.example.dilla.service;

import com.example.dilla.exception.NotFoundException;
import com.example.dilla.model.Category;
import com.example.dilla.model.Product;
import com.example.dilla.repository.CategoryRepository;
import com.example.dilla.repository.ProductRepository;
import com.example.dilla.repository.StockMutationRepository;
import com.example.dilla.repository.WarehouseStockRepository;
import com.example.dilla.Request.ProductRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final WarehouseStockRepository stockRepository;
    private final CategoryRepository categoryRepository;
    private final StockMutationRepository mutationRepository;

    public ProductService(ProductRepository repository,
                          WarehouseStockRepository stockRepository,
                          CategoryRepository categoryRepository,
                          StockMutationRepository mutationRepository) {
        this.repository = repository;
        this.stockRepository = stockRepository;
        this.categoryRepository = categoryRepository;
        this.mutationRepository = mutationRepository;
    }

    public Product create(ProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category tidak ditemukan"));

        if (request.getSkuCode() != null) {
            Product product = repository.findBySkuCode(request.getSkuCode());
            if (product != null){
                throw new RuntimeException("PRODUK DENGAN SKU " + request.getSkuCode() + "SUDAH DIGUNAKAN!");
            }
        }
        Product product = new Product();
        product.setName(request.getName());
        product.setSkuCode(request.getSkuCode());
        product.setPrice(request.getPrice());
        product.setCategory(category);
        product.setIsActive(true);

        return repository.save(product);
    }

    public List<Product> semuaProduk() {
        return repository.findAll();
    }

    public Product update(Long id, Product request) {
        Product product = getById(id);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setSkuCode(request.getSkuCode());
        product.setCategory(request.getCategory());
        return repository.save(product);
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .filter(Product::getIsActive)
                .orElseThrow(() -> new NotFoundException("Product tidak ditemukan"));
    }

    public void softDelete(Long id) {
        Product product = getById(id);
        product.setIsActive(false);
        repository.save(product);
    }

    public void restore(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product tidak ditemukan"));
        product.setIsActive(true);
        repository.save(product);
    }

    public List<?> getLowStock() {
        return stockRepository.findLowStock();
    }

    public Integer getStockSummary(String sku) {
        return stockRepository.getTotalStock(sku);
    }

    // hardDelete
    @Transactional
    public Product hardDelete(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product tidak ditemukan"));

        if (product.getIsActive()) {
            throw new RuntimeException("Product harus dinonaktifkan terlebih dahulu sebelum dihapus");
        }

        mutationRepository.nullifyProductId(id);

        repository.delete(product);
        return product;
    }

}