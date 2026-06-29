package com.ebac.auth_service.service;

import com.ebac.auth_service.dto.LoginRequest;
import com.ebac.auth_service.dto.RegisterRequest;
import com.ebac.auth_service.dto.TokenResponse;
import com.ebac.auth_service.entity.Usuario;
import com.ebac.auth_service.repository.UsuarioRepository;
import com.ebac.auth_service.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository repository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public Usuario registrar(RegisterRequest request) {

        if (repository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario(
                request.nome(),
                request.email(),
                encoder.encode(request.senha()),
                Usuario.Role.valueOf(request.role().toUpperCase())
        );

        return repository.save(usuario);
    }

    public TokenResponse login(LoginRequest request) {

        Usuario usuario = repository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!encoder.matches(request.senha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        String token = jwtUtil.gerarToken(usuario.getEmail(), usuario.getRole().name());
        return new TokenResponse(token);
    }
}