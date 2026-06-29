package com.ebac.auth_service.service;

import com.ebac.auth_service.dto.LoginRequest;
import com.ebac.auth_service.dto.RegisterRequest;
import com.ebac.auth_service.dto.TokenResponse;
import com.ebac.auth_service.dto.UsuarioResponse;
import com.ebac.auth_service.entity.Usuario;
import com.ebac.auth_service.exception.CredenciaisInvalidasException;
import com.ebac.auth_service.exception.EmailJaCadastradoException;
import com.ebac.auth_service.repository.UsuarioRepository;
import com.ebac.auth_service.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioRepository repository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(final UsuarioRepository repository, final JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public UsuarioResponse registrar(RegisterRequest request) {
        log.info("Registrando novo usuário: {}", request.email());

        if (repository.findByEmail(request.email()).isPresent()) {
            throw new EmailJaCadastradoException(request.email());
        }

        Usuario usuario = new Usuario(
                request.nome(),
                request.email(),
                encoder.encode(request.senha()),
                Usuario.Role.valueOf(request.role().toUpperCase())
        );

        repository.save(usuario);
        log.info("Usuário registrado com sucesso: {}", request.email());

        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole().name()
        );
    }

    public TokenResponse login(LoginRequest request) {
        log.info("Tentativa de login: {}", request.email());

        Usuario usuario = repository.findByEmail(request.email())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!encoder.matches(request.senha(), usuario.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        String token = jwtUtil.gerarToken(usuario.getEmail(), usuario.getRole().name());
        log.info("Login realizado com sucesso: {}", request.email());

        return new TokenResponse(token);
    }
}