package com.example.dilla.controller;

import com.example.dilla.model.Category;
import com.example.dilla.response.WebResponse;
import com.example.dilla.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService service;

    public CategoryController (CategoryService service) {
        this.service = service;
    }
    //create
    @PostMapping
    public WebResponse<Category> create (@RequestBody Category category) {
        return WebResponse.<Category>builder()
                .status("success")
                .message("category berhasil dibuat")
                .data(service.create(category))
                .build();
    }
    //get
    @GetMapping
    public WebResponse<List<Category>> getAll(){
        return WebResponse.<List<Category>>builder()
                .status("success")
                .data(service.getAll())
                .build();
    }
    //delete
    @DeleteMapping("/{id}")
    public WebResponse<String> delete(@PathVariable Long id) {
        service.delete(id);
        return WebResponse.<String>builder()
                .status("Success")
                .message("Category berhasil dihapus")
                .build();
    }
}

