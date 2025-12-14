package com.tuproyecto.levelupgamer.dto;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username; // Email
    private String password;
    private String name;      // Para React
    private String birthDate; // Para Android
    private String role;
}