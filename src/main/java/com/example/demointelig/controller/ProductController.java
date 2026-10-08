package com.example.demointelig.controller;

import com.example.demointelig.model.Product;
import com.example.demointelig.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    public final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getMethodName(@PathVariable Long id){
        Optional<Product> product = service.findById(id);
        if (product.isEmpty()){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.get());
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product){
        Product newProduct = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
        public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product productUpdated){
        Optional<Product> product = service.update(id, productUpdated);
        if (product.isPresent()){

            return ResponseEntity.ok(product.get());
        }
        return ResponseEntity.notFound().build();
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

        /*@DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if (!deleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
        }
*/
}
