package com.tuproyecto.levelupgamer.controller;

import com.tuproyecto.levelupgamer.model.Product;
import com.tuproyecto.levelupgamer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired private ProductRepository productRepository;

    // 1. LISTAR TODOS
    @GetMapping
    public List<Product> getAllProducts() { return productRepository.findAll(); }

    // 2. NUEVO: BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Producto no encontrado con id " + id));
        return ResponseEntity.ok(product);
    }

    // 3. AGREGAR
    @PostMapping
    public Product createProduct(@RequestBody Product product) { return productRepository.save(product); }

    // 4. MODIFICAR
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product details) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(details.getName());
        product.setPrice(details.getPrice());
        product.setDescription(details.getDescription());
        product.setImageUrl(details.getImageUrl());
        return productRepository.save(product);
    }

    // 5. ELIMINAR (Con mensaje de confirmación)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        // Verificamos si existe antes de borrar
        if (!productRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Error: El producto no existe");
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }
}