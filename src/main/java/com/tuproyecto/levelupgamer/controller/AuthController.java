package com.tuproyecto.levelupgamer.controller;

import com.tuproyecto.levelupgamer.User; // Tu Entidad
import com.tuproyecto.levelupgamer.repository.UserRepository; // Repositorio de User
import com.tuproyecto.levelupgamer.dto.AuthResponse; // DTO de respuesta
import com.tuproyecto.levelupgamer.dto.LoginRequest; // DTO de login
import com.tuproyecto.levelupgamer.dto.RegisterRequest; // DTO de registro
import com.tuproyecto.levelupgamer.service.JwtService; // Servicio de JWT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // URL base para esta clase
public class AuthController {

    // (1) Inyectamos todas las herramientas que necesitamos
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // El que encripta

    @Autowired
    private JwtService jwtService; // El que crea el token

    @Autowired
    private AuthenticationManager authenticationManager; // El que valida el login

    // --- (2) ENDPOINT DE REGISTRO ---
    // Responde a POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        
        // Opcional: Verificar si el email ya existe
        if (userRepository.findByEmail(request.getUsername()).isPresent()) {
            // Retornamos un "400 Bad Request"
            return ResponseEntity.badRequest().body(new AuthResponse("Error: El email ya está en uso."));
        }

        // Creamos un nuevo usuario
        User newUser = new User();
        newUser.setEmail(request.getUsername()); // React envía 'username' como email
        // Encriptamos la contraseña ANTES de guardarla
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        
        // Damos valores por defecto a los campos de tu ProfilePage
        newUser.setName("Nuevo Usuario"); // Nombre temporal
        newUser.setRole("user"); // Rol por defecto
        newUser.setPoints(0); // Puntos iniciales
        // lastRewardClaimed es null por defecto

        // Guardamos el usuario en la base de datos
        User savedUser = userRepository.save(newUser);

        // Generamos un token para que inicie sesión automáticamente
        // (Usamos el UserDetails de Spring)
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            savedUser.getEmail(),
            savedUser.getPassword(),
            java.util.Collections.emptyList() // Lista de roles (vacía)
        );
        
        String token = jwtService.generateToken(userDetails);

        // Devolvemos el token (un "200 OK")
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // --- (3) ENDPOINT DE LOGIN ---
    // Responde a POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        
        // (A) Usamos el AuthenticationManager para validar al usuario
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), // El email
                request.getPassword()  // La contraseña en texto plano
            )
        );
        
        // (B) Si llegamos aquí, ¡el login es exitoso!
        // El 'authentication' contiene los detalles del usuario
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // (C) Creamos un token para él
        String token = jwtService.generateToken(userDetails);

        // (D) Devolvemos el token en la respuesta
        return ResponseEntity.ok(new AuthResponse(token));
    }
}