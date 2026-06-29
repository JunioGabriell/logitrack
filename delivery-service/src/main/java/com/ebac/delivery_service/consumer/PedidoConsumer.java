package com.ebac.delivery_service.consumer;

import com.ebac.delivery_service.dto.PedidoEvent;
import com.ebac.delivery_service.model.Entrega;
import com.ebac.delivery_service.repository.EntregaRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {

    private final EntregaRepository repository;

    public PedidoConsumer(EntregaRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "pedidos", groupId = "delivery-grupo")
    public void consumir(PedidoEvent event) {

        Entrega entrega = new Entrega(
                event.id(),
                event.clienteEmail(),
                event.descricao(),
                event.endereco()
        );

        repository.save(entrega);
        System.out.println("📦 Entrega criada para pedido: " + event.id());
    }
}