package com.tuproyecto.levelupgamer.controller;

import com.tuproyecto.levelupgamer.model.User;
import com.tuproyecto.levelupgamer.dto.*;
import com.tuproyecto.levelupgamer.repository.UserRepository;
import com.tuproyecto.levelupgamer.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;
    @Autowired private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse("Error: Email en uso"));
        }
        User newUser = new User();
        newUser.setEmail(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setName(request.getName() != null ? request.getName() : "Usuario");
        newUser.setBirthDate(request.getBirthDate());
        newUser.setRole("user");
        newUser.setPoints(0);

        userRepository.save(newUser);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            newUser.getEmail(), newUser.getPassword(), java.util.Collections.emptyList()
        );
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", newUser.getRole());

        return ResponseEntity.ok(new AuthResponse(jwtService.generateToken(extraClaims, userDetails)));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User dbUser = userRepository.findByEmail(request.getUsername()).orElseThrow();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
             dbUser.getEmail(), dbUser.getPassword(), java.util.Collections.emptyList()
        );
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", dbUser.getRole());

        return ResponseEntity.ok(new AuthResponse(jwtService.generateToken(extraClaims, userDetails)));
    }
}