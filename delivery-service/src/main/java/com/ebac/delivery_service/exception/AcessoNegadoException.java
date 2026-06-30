package com.ebac.delivery_service.exception;

public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException() {
        super("Acesso negado a esta entrega");
    }
}
