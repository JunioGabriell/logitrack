package com.ebac.user_service.controller;

import com.ebac.user_service.dto.UsuarioRequest;
import com.ebac.user_service.dto.UsuarioResponse;
import com.ebac.user_service.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
@SecurityRequirement(name = "Bearer Authentication")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(final UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários", description = "Apenas ADMIN")
    public ResponseEntity<Page<UsuarioResponse>> listarTodos(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listarTodos(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca usuário por ID")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados do usuário")
    public ResponseEntity<UsuarioResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta usuário", description = "Apenas ADMIN")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}