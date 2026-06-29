package com.ebac.order_service.producer;

import com.ebac.order_service.dto.PedidoEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    private final KafkaTemplate<String, PedidoEvent> kafkaTemplate;

    public PedidoProducer(KafkaTemplate<String, PedidoEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicar(PedidoEvent event) {
        kafkaTemplate.send("pedidos", event.id().toString(), event);
        System.out.println("✅ Pedido publicado no Kafka: " + event.id());
    }
}