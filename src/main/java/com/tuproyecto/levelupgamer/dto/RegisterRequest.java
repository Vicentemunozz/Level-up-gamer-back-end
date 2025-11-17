package com.tuproyecto.levelupgamer.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    // Los nombres deben coincidir con el JSON que envía React
    private String username; // Este será el email
    private String password;
}