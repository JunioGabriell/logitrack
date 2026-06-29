package com.ebac.auth_service.dto;

public record RegisterRequest(String nome, String email, String senha, String role) {}
