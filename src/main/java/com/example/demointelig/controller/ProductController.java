package com.example.demointelig.controller;

import com.example.demointelig.dto.DiscountRequest;
import com.example.demointelig.dto.ProductRequest;
import com.example.demointelig.dto.ProductResponse;
import com.example.demointelig.model.Product;
import com.example.demointelig.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<ProductResponse> list = new ArrayList<>();
        for (Product elem : service.findAll()) {
            list.add(toResponse(elem));
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        Product product = service.findById(id).orElseThrow(
                ()->
                        new ResourceAccessException("Producto no encontrado" +id)
        );

        return ResponseEntity.ok(toResponse(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product) {
        Product productSaved = service.save(toEntity(product), product.categoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(productSaved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest productUpdated) {
        Product product = service.update
                (id,
                        toEntity(productUpdated),
                        productUpdated.categoryId()
                );
        return ResponseEntity.ok(toResponse(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/discount")
    public ResponseEntity<ProductResponse> applyDiscount(
            @PathVariable Long id,
            @Valid @RequestBody DiscountRequest request
    ) {
        Product updatedProduct = service.applyDiscount(id, request.percentage());
        return ResponseEntity.ok(toResponse(updatedProduct));
    }


    private ProductResponse toResponse(Product product) {
        String categoryName = product.getCategory() != null ? product.getCategory().getName() : null;
        Long categoryId = product.getCategory() != null ? product.getCategory().getId() : null;

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                categoryName,
                categoryId
        );
    }

    private Product toEntity(ProductRequest product) {
        Product productEntity = new Product();
        productEntity.setName(product.name());
        productEntity.setPrice(product.price());
        productEntity.setStock(product.stock());
        return productEntity;
    }
}