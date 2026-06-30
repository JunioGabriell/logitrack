package com.ebac.delivery_service.dto;

import jakarta.validation.constraints.NotBlank;

public record AtualizarStatusRequest(

        @NotBlank(message = "Status é obrigatório")
        String status
) {}