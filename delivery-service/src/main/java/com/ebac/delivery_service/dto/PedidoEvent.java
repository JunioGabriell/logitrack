package com.ebac.delivery_service.dto;

public record PedidoEvent(
        Long id,
        String clienteEmail,
        String descricao,
        String endereco,
        String status
) {}