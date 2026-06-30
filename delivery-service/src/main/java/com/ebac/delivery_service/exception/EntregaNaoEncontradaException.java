package com.ebac.delivery_service.exception;

public class EntregaNaoEncontradaException extends RuntimeException {
    public EntregaNaoEncontradaException(String id) {
        super("Entrega não encontrada com ID: " + id);
    }
}
