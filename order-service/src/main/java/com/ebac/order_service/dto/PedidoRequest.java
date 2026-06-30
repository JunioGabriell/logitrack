package com.ebac.order_service.dto;

import jakarta.validation.constraints.NotBlank;

public record PedidoRequest(

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotBlank(message = "Endereço é obrigatório")
        String endereco

) {}