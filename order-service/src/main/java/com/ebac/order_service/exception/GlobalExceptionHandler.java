package com.ebac.order_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        HashMap<String, String> erros = new HashMap<>();
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            erros.put(erro.getField(), erro.getDefaultMessage());
        }

        log.warn("Erro de validação: {}", erros);
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<HashMap<String, String>> handlePedidoNaoEncontrado(
            PedidoNaoEncontradoException ex) {

        log.warn("Pedido não encontrado: {}", ex.getMessage());

        HashMap<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(AcessoNegadoException.class)
    public ResponseEntity<HashMap<String, String>> handleAcessoNegado(
            AcessoNegadoException ex) {

        log.warn("Acesso negado: {}", ex.getMessage());

        HashMap<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
    }
}