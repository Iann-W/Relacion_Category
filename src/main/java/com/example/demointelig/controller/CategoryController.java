package com.example.demointelig.controller;

import com.example.demointelig.dto.CategoryRequest;
import com.example.demointelig.dto.CategoryResponse;
import com.example.demointelig.exeption.ResourceNotFoundException;
import com.example.demointelig.model.Category;
import com.example.demointelig.repository.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository repoCategory;

    public CategoryController(CategoryRepository repoCategory) {
        this.repoCategory = repoCategory;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        List<CategoryResponse> list = new ArrayList<>();
        for (Category elem : repoCategory.findAll()) {
            list.add(toResponse(elem));
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        Category category = repoCategory.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return ResponseEntity.ok(toResponse(category));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category categorySaved = repoCategory.save(toEntity(categoryRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(categorySaved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest) {
        Category categoryBd = repoCategory.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        categoryBd.setName(categoryRequest.name());
        Category updatedCategory = repoCategory.save(categoryBd);

        return ResponseEntity.ok(toResponse(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Category categoryBd = repoCategory.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        repoCategory.delete(categoryBd);
        return ResponseEntity.noContent().build();
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    private Category toEntity(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        return category;
    }
}