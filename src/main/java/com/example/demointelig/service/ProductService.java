package com.example.demointelig.service;

import com.example.demointelig.exeption.ResourceNotFoundException;
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
        Category category = repoCategory.
                findById(categoryId).
                orElseThrow(
                        () ->
                                new ResourceNotFoundException("Category not found with id: "+categoryId));

        product.setCategory(category);
        return repo.save(product);
    }

    public Optional<Product> update(Long id, Product newProduct, Long categoryId) {



        Product productBd = repo.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("Product not Found" +
                                " with id " +id)
        );
        /*if (optional.isEmpty()) {
            return Optional.empty();
        }*/

        Category category = repoCategory.findById(categoryId).orElseThrow(
                ()->
                        new ResourceNotFoundException("Category not found with id"+categoryId)
        );

        //Product productBd = optional.get();
        productBd.setName(newProduct.getName()+"");
        productBd.setPrice((double)newProduct.getPrice());
        productBd.setStock(newProduct.getStock());
        productBd.setCategory(category);

        // Si el objeto nuevo trae una categoría asociada, actualízala también
        /*if (newProduct.getCategory() != null) {
            productBd.setCategory(newProduct.getCategory());
        }*/
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