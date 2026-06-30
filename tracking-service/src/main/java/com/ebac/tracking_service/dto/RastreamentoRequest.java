package com.ebac.tracking_service.dto;

import jakarta.validation.constraints.NotBlank;

public record RastreamentoRequest(

        @NotBlank(message = "PedidoId é obrigatório")
        String pedidoId,

        @NotBlank(message = "Status é obrigatório")
        String status,

        @NotBlank(message = "Localização é obrigatória")
        String localizacao

) {}