package com.example.dilla.service;

import com.example.dilla.exception.NotFoundException;
import com.example.dilla.model.Category;
import com.example.dilla.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //create
    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Category sudah ada");
        }
        return categoryRepository.save(category);
    }

    //get all
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    //get by id
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Category tidak ditemukan"));
    }

    //update
    public Category update(Long id, Category request) {

        Category category = getById(id);
        category.setName(request.getName());

        return categoryRepository.save(category);
    }
    //delete
    public void delete(Long id) {
        Category category = getById(id);

        categoryRepository.delete(category);
    }
}
