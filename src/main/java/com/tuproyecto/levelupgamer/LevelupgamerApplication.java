package com.tuproyecto.levelupgamer;

import com.tuproyecto.levelupgamer.repository.*;
import com.tuproyecto.levelupgamer.model.Product;
import com.tuproyecto.levelupgamer.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LevelupgamerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LevelupgamerApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(UserRepository userRepo, ProductRepository productRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // Cargar productos si está vacío
            if (productRepo.count() == 0) {
                productRepo.save(new Product(null, "Catan", 29990.0, "Juego de estrategia", "https://m.media-amazon.com/images/I/81DATAXAIsL._AC_SL1500_.jpg"));
                productRepo.save(new Product(null, "Xbox Controller", 59990.0, "Mando original", "https://m.media-amazon.com/images/I/61z3GKYizVL._AC_SL1500_.jpg"));
                productRepo.save(new Product(null, "PS5", 549990.0, "Consola Next-Gen", "https://m.media-amazon.com/images/I/51051FiD9UL._AC_SL1000_.jpg"));
                System.out.println("✅ Datos insertados.");
            }

            // Crear admin si no existe
            if (userRepo.findByEmail("admin@duoc.cl").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@duoc.cl");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setName("Administrador");
                admin.setRole("admin");
                userRepo.save(admin);
                System.out.println("👑 Admin creado.");
            }
        };
    }
}