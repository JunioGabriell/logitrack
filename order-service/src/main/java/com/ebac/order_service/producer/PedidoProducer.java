package com.ebac.order_service.producer;

import com.ebac.order_service.dto.PedidoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    private static final Logger log = LoggerFactory.getLogger(PedidoProducer.class);

    private static final String TOPICO = "pedidos";

    private final KafkaTemplate<String, PedidoEvent> kafkaTemplate;

    public PedidoProducer(final KafkaTemplate<String, PedidoEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicar(PedidoEvent event) {
        kafkaTemplate.send(TOPICO, event.id().toString(), event);
        log.info("Pedido publicado no Kafka — ID: {}", event.id());
    }
}