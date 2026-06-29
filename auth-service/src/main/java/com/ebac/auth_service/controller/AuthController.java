package com.ebac.auth_service.controller;

import com.ebac.auth_service.dto.LoginRequest;
import com.ebac.auth_service.dto.RegisterRequest;
import com.ebac.auth_service.dto.TokenResponse;
import com.ebac.auth_service.dto.UsuarioResponse;
import com.ebac.auth_service.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Login e registro de usuários")
public class AuthController {

    private final AuthService service;

    public AuthController(final AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    @Operation(summary = "Registra novo usuário")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Realiza login e retorna token JWT")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}