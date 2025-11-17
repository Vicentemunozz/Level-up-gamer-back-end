package com.tuproyecto.levelupgamer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate; // <-- Importamos el tipo de dato para la fecha
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users") // <-- La tabla se llamará "users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tu React usa 'email' como 'username' [cite: 19, 102]
    // Lo hacemos único y no nulo
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false) // No puede ser nulo
    private String password;

    // Campos que vimos en tu ProfilePage.jsx [cite: 539, 541, 545]
    private String name;
    private String role; // "user" o "admin"
    private int points;

    // Campo para la recompensa diaria [cite: 533]
    private LocalDate lastRewardClaimed;
}