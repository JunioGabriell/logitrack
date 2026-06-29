package com.ebac.tracking_service.dto;

public record RastreamentoResponse(
        String id,
        String pedidoId,
        String status,
        String localizacao,
        String atualizadoPor,
        String atualizadoEm
) {}