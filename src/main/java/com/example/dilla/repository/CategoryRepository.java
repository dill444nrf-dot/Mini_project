package com.example.dilla.repository;

import com.example.dilla.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    //cari nama
    Optional<Category> findByName(String name);

    //cek nama
    boolean existsByName(String name);
}
