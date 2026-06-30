package com.ebac.order_service.exception;

public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException() {
        super("Acesso negado a este pedido");
    }
}