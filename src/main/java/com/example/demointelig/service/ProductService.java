package com.example.demointelig.service;

import com.example.demointelig.model.Category;
import com.example.demointelig.model.Product;
import com.example.demointelig.repository.CategoryRepository;
import com.example.demointelig.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final CategoryRepository repoCategory;

    public ProductService(ProductRepository repo, CategoryRepository repoCategory) {
        this.repo = repo;
        this.repoCategory = repoCategory;
    }

    public List<Product> findAll() {
        return repo.findAll();
    }

    public Optional<Product> findById(Long id) {
        return repo.findById(id);
    }

    public Product save(Product product, Long categoryId) {
        Category category = repoCategory.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con el ID: " + categoryId));

        product.setCategory(category);
        return repo.save(product);
    }

    public Optional<Product> update(Long id, Product newProduct) {
        Optional<Product> optional = repo.findById(id);
        if (optional.isEmpty()) {
            return Optional.empty();
        }

        Product productBd = optional.get();
        productBd.setName(newProduct.getName());
        productBd.setPrice(newProduct.getPrice());
        productBd.setStock(newProduct.getStock());

        // Si el objeto nuevo trae una categoría asociada, actualízala también
        if (newProduct.getCategory() != null) {
            productBd.setCategory(newProduct.getCategory());
        }

        return Optional.of(repo.save(productBd));
    }

    public boolean delete(Long id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}