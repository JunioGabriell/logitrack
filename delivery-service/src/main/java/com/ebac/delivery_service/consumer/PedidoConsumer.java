package com.ebac.delivery_service.consumer;

import com.ebac.delivery_service.dto.PedidoEvent;
import com.ebac.delivery_service.model.Entrega;
import com.ebac.delivery_service.repository.EntregaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {

    private static final Logger log = LoggerFactory.getLogger(PedidoConsumer.class);

    private final EntregaRepository repository;

    public PedidoConsumer(final EntregaRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "pedidos", groupId = "delivery-grupo")
    public void consumir(PedidoEvent event) {
        log.info("Pedido recebido do Kafka — ID: {}", event.id());

        Entrega entrega = new Entrega(
                event.id(),
                event.clienteEmail(),
                event.descricao(),
                event.endereco()
        );

        repository.save(entrega);
        log.info("Entrega criada no MongoDB — pedido ID: {}", event.id());
    }
}