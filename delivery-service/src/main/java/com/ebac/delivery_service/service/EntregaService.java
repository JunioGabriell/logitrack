package com.ebac.delivery_service.service;

import com.ebac.delivery_service.dto.AtualizarStatusRequest;
import com.ebac.delivery_service.dto.EntregaResponse;
import com.ebac.delivery_service.model.Entrega;
import com.ebac.delivery_service.repository.EntregaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntregaService {

    private final EntregaRepository repository;

    public EntregaService(EntregaRepository repository) {
        this.repository = repository;
    }

    public List<EntregaResponse> listar(String email, String role) {
        if (role.equals("ADMIN")) {
            return repository.findAll().stream().map(this::toResponse).toList();
        }
        return repository.findByDriverEmail(email).stream().map(this::toResponse).toList();
    }

    public EntregaResponse buscarPorId(String id, String email, String role) {
        Entrega entrega = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));

        if (role.equals("DRIVER") && !email.equals(entrega.getDriverEmail())) {
            throw new RuntimeException("Acesso negado");
        }

        return toResponse(entrega);
    }

    public EntregaResponse atualizarStatus(String id, AtualizarStatusRequest request,
                                           String email, String role) {
        Entrega entrega = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada"));

        if (role.equals("DRIVER") && !email.equals(entrega.getDriverEmail())) {
            throw new RuntimeException("Acesso negado");
        }

        entrega.setStatus(Entrega.Status.valueOf(request.status().toUpperCase()));
        entrega.setAtualizadoEm(LocalDateTime.now());
        repository.save(entrega);

        return toResponse(entrega);
    }

    private EntregaResponse toResponse(Entrega entrega) {
        return new EntregaResponse(
                entrega.getId(),
                entrega.getPedidoId(),
                entrega.getClienteEmail(),
                entrega.getDescricao(),
                entrega.getEndereco(),
                entrega.getDriverEmail(),
                entrega.getStatus().name(),
                entrega.getCriadoEm(),
                entrega.getAtualizadoEm()
        );
    }
}