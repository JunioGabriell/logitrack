package com.ebac.delivery_service.controller;

import com.ebac.delivery_service.dto.AtualizarStatusRequest;
import com.ebac.delivery_service.dto.EntregaResponse;
import com.ebac.delivery_service.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveries")
@Tag(name = "Entregas", description = "Gerenciamento de entregas")
@SecurityRequirement(name = "Bearer Authentication")
public class EntregaController {

    private final EntregaService service;

    public EntregaController(final EntregaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista entregas", description = "ADMIN vê todas, DRIVER vê as suas")
    public ResponseEntity<Page<EntregaResponse>> listar(
            @PageableDefault(size = 10) Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();
        String role = authentication.getAuthorities().iterator().next()
                .getAuthority().replace("ROLE_", "");

        return ResponseEntity.ok(service.listar(email, role, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca entrega por ID")
    public ResponseEntity<EntregaResponse> buscarPorId(
            @PathVariable String id,
            Authentication authentication) {

        String email = authentication.getName();
        String role = authentication.getAuthorities().iterator().next()
                .getAuthority().replace("ROLE_", "");

        return ResponseEntity.ok(service.buscarPorId(id, email, role));
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Atualiza status da entrega", description = "ADMIN e DRIVER")
    public ResponseEntity<EntregaResponse> atualizarStatus(
            @PathVariable String id,
            @RequestBody @Valid AtualizarStatusRequest request,
            Authentication authentication) {

        String email = authentication.getName();
        String role = authentication.getAuthorities().iterator().next()
                .getAuthority().replace("ROLE_", "");

        return ResponseEntity.ok(service.atualizarStatus(id, request, email, role));
    }
}