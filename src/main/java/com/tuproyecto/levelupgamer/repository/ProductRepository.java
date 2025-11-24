package com.tuproyecto.levelupgamer.repository;
import com.tuproyecto.levelupgamer.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}