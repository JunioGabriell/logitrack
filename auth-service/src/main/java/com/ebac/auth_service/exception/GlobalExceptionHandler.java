package com.ebac.auth_service.exception;

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

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<HashMap<String, String>> handleEmailJaCadastrado(
            EmailJaCadastradoException ex) {

        log.warn("Tentativa de registro com email duplicado: {}", ex.getMessage());

        HashMap<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        return ResponseEntity.badRequest().body(erro);
    }

    @ExceptionHandler(CredenciaisInvalidasException.class)
    public ResponseEntity<HashMap<String, String>> handleCredenciaisInvalidas(
            CredenciaisInvalidasException ex) {

        log.warn("Tentativa de login com credenciais inválidas");

        HashMap<String, String> erro = new HashMap<>();
        erro.put("erro", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }
}