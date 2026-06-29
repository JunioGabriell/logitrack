package com.ebac.order_service.dto;

import java.time.LocalDateTime;

public record PedidoResponse(
        Long id,
        String clienteEmail,
        String descricao,
        String endereco,
        String status,
        LocalDateTime criadoEm
) {}