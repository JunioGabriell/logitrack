package com.ebac.order_service.service;

import com.ebac.order_service.dto.PedidoEvent;
import com.ebac.order_service.dto.PedidoRequest;
import com.ebac.order_service.dto.PedidoResponse;
import com.ebac.order_service.entity.Pedido;
import com.ebac.order_service.exception.AcessoNegadoException;
import com.ebac.order_service.exception.PedidoNaoEncontradoException;
import com.ebac.order_service.producer.PedidoProducer;
import com.ebac.order_service.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository repository;
    private final PedidoProducer producer;

    public PedidoService(final PedidoRepository repository, final PedidoProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    @Transactional
    public PedidoResponse criar(PedidoRequest request, String clienteEmail) {
        log.info("Criando pedido para cliente: {}", clienteEmail);

        Pedido pedido = new Pedido(clienteEmail, request.descricao(), request.endereco());
        repository.save(pedido);

        producer.publicar(new PedidoEvent(
                pedido.getId(),
                pedido.getClienteEmail(),
                pedido.getDescricao(),
                pedido.getEndereco(),
                pedido.getStatus().name()
        ));

        log.info("Pedido criado com sucesso — ID: {}", pedido.getId());
        return toResponse(pedido);
    }

    public Page<PedidoResponse> listar(String email, String role, Pageable pageable) {
        log.info("Listando pedidos — role: {}", role);

        if (role.equals("ADMIN")) {
            return repository.findAll(pageable).map(this::toResponse);
        }
        return repository.findByClienteEmail(email, pageable).map(this::toResponse);
    }

    public PedidoResponse buscarPorId(Long id, String email, String role) {
        log.info("Buscando pedido ID: {}", id);

        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));

        if (role.equals("CLIENT") && !pedido.getClienteEmail().equals(email)) {
            throw new AcessoNegadoException();
        }

        return toResponse(pedido);
    }

    private PedidoResponse toResponse(Pedido pedido) {
        return new PedidoResponse(
                pedido.getId(),
                pedido.getClienteEmail(),
                pedido.getDescricao(),
                pedido.getEndereco(),
                pedido.getStatus().name(),
                pedido.getCriadoEm()
        );
    }
}