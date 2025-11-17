package com.tuproyecto.levelupgamer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // <-- Esto es de Lombok: crea getters, setters, constructores, etc.
@NoArgsConstructor // <-- Constructor vacío (útil para JPA)
@AllArgsConstructor // <-- Constructor con todos los campos
@Entity // <-- Le dice a Spring que esto es una entidad (tabla)
@Table(name = "products") // <-- Opcional: Nombra la tabla en plural
public class Product {

    @Id // <-- Marca este campo como la Llave Primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <-- La hace autoincremental
    private Long id;

    private String name;
    private int price; // Usamos 'int' basado en tus datos mock [cite: 814]
    private String description;

    // Lo llamamos 'imageUrl' para que coincida con tu código React 
    private String imageUrl; 
}