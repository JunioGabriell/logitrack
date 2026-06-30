package com.ebac.delivery_service.service;

import com.ebac.delivery_service.dto.AtualizarStatusRequest;
import com.ebac.delivery_service.dto.EntregaResponse;
import com.ebac.delivery_service.exception.AcessoNegadoException;
import com.ebac.delivery_service.exception.EntregaNaoEncontradaException;
import com.ebac.delivery_service.model.Entrega;
import com.ebac.delivery_service.repository.EntregaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntregaService {

    private static final Logger log = LoggerFactory.getLogger(EntregaService.class);

    private final EntregaRepository repository;

    public EntregaService(final EntregaRepository repository) {
        this.repository = repository;
    }

    public Page<EntregaResponse> listar(String email, String role, Pageable pageable) {
        log.info("Listando entregas — role: {}", role);

        if (role.equals("ADMIN")) {
            return repository.findAll(pageable).map(this::toResponse);
        }
        return repository.findByDriverEmail(email, pageable).map(this::toResponse);
    }

    public EntregaResponse buscarPorId(String id, String email, String role) {
        log.info("Buscando entrega ID: {}", id);

        Entrega entrega = repository.findById(id)
                .orElseThrow(() -> new EntregaNaoEncontradaException(id));

        if (role.equals("DRIVER") && !email.equals(entrega.getDriverEmail())) {
            throw new AcessoNegadoException();
        }

        return toResponse(entrega);
    }

    @Transactional
    public EntregaResponse atualizarStatus(String id, AtualizarStatusRequest request,
                                           String email, String role) {
        log.info("Atualizando status da entrega ID: {} para: {}", id, request.status());

        Entrega entrega = repository.findById(id)
                .orElseThrow(() -> new EntregaNaoEncontradaException(id));

        if (role.equals("DRIVER") && !email.equals(entrega.getDriverEmail())) {
            throw new AcessoNegadoException();
        }

        entrega.setStatus(Entrega.Status.valueOf(request.status().toUpperCase()));
        entrega.setAtualizadoEm(java.time.LocalDateTime.now());
        repository.save(entrega);

        log.info("Status da entrega atualizado com sucesso — ID: {}", id);
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