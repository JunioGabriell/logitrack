package com.ebac.delivery_service.dto;

import java.time.LocalDateTime;

public record EntregaResponse(
        String id,
        Long pedidoId,
        String clienteEmail,
        String descricao,
        String endereco,
        String driverEmail,
        String status,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {}