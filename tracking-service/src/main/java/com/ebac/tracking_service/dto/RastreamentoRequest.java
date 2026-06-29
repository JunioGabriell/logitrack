package com.ebac.tracking_service.dto;

public record RastreamentoRequest(
        String pedidoId,
        String status,
        String localizacao
) {}
