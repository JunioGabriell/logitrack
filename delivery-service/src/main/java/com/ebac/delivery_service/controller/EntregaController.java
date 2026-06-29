package com.ebac.delivery_service.controller;

import com.ebac.delivery_service.dto.AtualizarStatusRequest;
import com.ebac.delivery_service.dto.EntregaResponse;
import com.ebac.delivery_service.service.EntregaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@Tag(name = "Entregas", description = "Gerenciamento de entregas")
public class EntregaController {

    private final EntregaService service;

    public EntregaController(EntregaService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Lista entregas", description = "ADMIN vê todas, DRIVER vê só as suas")
    public ResponseEntity<List<EntregaResponse>> listar(Authentication authentication) {
        String email = authentication.getName();
        String role = authentication.getAuthorities().iterator().next()
                .getAuthority().replace("ROLE_", "");
        return ResponseEntity.ok(service.listar(email, role));
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
    @Operation(summary = "Atualiza status da entrega", description = "DRIVER e ADMIN")
    public ResponseEntity<EntregaResponse> atualizarStatus(
            @PathVariable String id,
            @RequestBody AtualizarStatusRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        String role = authentication.getAuthorities().iterator().next()
                .getAuthority().replace("ROLE_", "");
        return ResponseEntity.ok(service.atualizarStatus(id, request, email, role));
    }
}