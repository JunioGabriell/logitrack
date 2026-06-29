package com.ebac.order_service.service;

import com.ebac.order_service.dto.PedidoEvent;
import com.ebac.order_service.dto.PedidoRequest;
import com.ebac.order_service.dto.PedidoResponse;
import com.ebac.order_service.entity.Pedido;
import com.ebac.order_service.producer.PedidoProducer;
import com.ebac.order_service.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoProducer producer;

    public PedidoService(PedidoRepository repository, PedidoProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }

    public PedidoResponse criar(PedidoRequest request, String clienteEmail) {
        Pedido pedido = new Pedido(clienteEmail, request.descricao(), request.endereco());
        repository.save(pedido);

        producer.publicar(new PedidoEvent(
                pedido.getId(),
                pedido.getClienteEmail(),
                pedido.getDescricao(),
                pedido.getEndereco(),
                pedido.getStatus().name()
        ));

        return toResponse(pedido);
    }

    public List<PedidoResponse> listar(String email, String role) {
        if (role.equals("ADMIN")) {
            return repository.findAll().stream().map(this::toResponse).toList();
        }
        return repository.findByClienteEmail(email).stream().map(this::toResponse).toList();
    }

    public PedidoResponse buscarPorId(Long id, String email, String role) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (role.equals("CLIENT") && !pedido.getClienteEmail().equals(email)) {
            throw new RuntimeException("Acesso negado");
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