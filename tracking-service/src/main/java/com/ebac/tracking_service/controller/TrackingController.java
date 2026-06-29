package com.ebac.tracking_service.controller;

import com.ebac.tracking_service.dto.RastreamentoRequest;
import com.ebac.tracking_service.dto.RastreamentoResponse;
import com.ebac.tracking_service.service.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracking")
@Tag(name = "Rastreamento", description = "Rastreamento de pedidos via SQS e DynamoDB")
public class TrackingController {

    private final TrackingService service;

    public TrackingController(TrackingService service) {
        this.service = service;
    }

    @GetMapping("/{pedidoId}")
    @Operation(summary = "Busca rastreamento do pedido", description = "CLIENT, ADMIN e DRIVER")
    public ResponseEntity<List<RastreamentoResponse>> buscar(@PathVariable String pedidoId) {
        return ResponseEntity.ok(service.buscarPorPedido(pedidoId));
    }

    @PostMapping("/{pedidoId}/update")
    @Operation(summary = "Atualiza rastreamento", description = "DRIVER e ADMIN")
    public ResponseEntity<RastreamentoResponse> atualizar(
            @PathVariable String pedidoId,
            @RequestBody RastreamentoRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(service.atualizar(request, email));
    }
}
