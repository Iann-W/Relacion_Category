package com.example.demointelig.service;

import com.example.demointelig.exeption.ResourceNotFoundException;
import com.example.demointelig.model.Category;
import com.example.demointelig.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repoCategory;

    public CategoryService(CategoryRepository repoCategory) {
        this.repoCategory = repoCategory;
    }

    public List<Category> findAll() {
        return repoCategory.findAll();
    }

    public Category findById(Long id) {
        return repoCategory.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public Category save(Category category) {
        return repoCategory.save(category);
    }

    public Category update(Long id, Category categoryUpdated) {
        Category categoryBd = findById(id);
        categoryBd.setName(categoryUpdated.getName());
        return repoCategory.save(categoryBd);
    }

    public void delete(Long id) {
        Category category = findById(id);
        repoCategory.delete(category);
    }
}