package com.ebac.order_service.controller;

import com.ebac.order_service.dto.PedidoRequest;
import com.ebac.order_service.dto.PedidoResponse;
import com.ebac.order_service.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Cria novo pedido", description = "CLIENT e ADMIN")
    public ResponseEntity<PedidoResponse> criar(
            @RequestBody PedidoRequest request,

            @AuthenticationPrincipal String email,
            Authentication authentication) {

        String role = authentication.getAuthorities().iterator().next()
                .getAuthority().replace("ROLE_", "");

        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request, email));
    }

    @GetMapping
    @Operation(summary = "Lista pedidos", description = "ADMIN vê todos, CLIENT vê só os seus")
    public ResponseEntity<List<PedidoResponse>> listar(Authentication authentication) {
        String email = authentication.getName();
        String role = authentication.getAuthorities().iterator().next()
                .getAuthority().replace("ROLE_", "");
        return ResponseEntity.ok(service.listar(email, role));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca pedido por ID")
    public ResponseEntity<PedidoResponse> buscarPorId(
            @PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        String role = authentication.getAuthorities().iterator().next()
                .getAuthority().replace("ROLE_", "");
        return ResponseEntity.ok(service.buscarPorId(id, email, role));
    }
}