package com.ebac.tracking_service.service;

import com.ebac.tracking_service.model.Rastreamento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamoDbService {

    private static final Logger log = LoggerFactory.getLogger(DynamoDbService.class);

    private final DynamoDbClient dynamoDbClient;

    @Value("${aws.dynamodb.table-name}")
    private String tableName;

    public DynamoDbService(final DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void salvar(Rastreamento rastreamento) {
        HashMap<String, AttributeValue> item = new HashMap<>();
        item.put("id", AttributeValue.builder().s(rastreamento.getId()).build());
        item.put("pedidoId", AttributeValue.builder().s(rastreamento.getPedidoId()).build());
        item.put("status", AttributeValue.builder().s(rastreamento.getStatus()).build());
        item.put("localizacao", AttributeValue.builder().s(rastreamento.getLocalizacao()).build());
        item.put("atualizadoPor", AttributeValue.builder().s(rastreamento.getAtualizadoPor()).build());
        item.put("atualizadoEm", AttributeValue.builder().s(rastreamento.getAtualizadoEm()).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
        log.info("Rastreamento salvo no DynamoDB — ID: {}", rastreamento.getId());
    }

    public List<Rastreamento> buscarPorPedidoId(String pedidoId) {
        log.info("Buscando rastreamentos do pedido: {}", pedidoId);

        ScanRequest request = ScanRequest.builder()
                .tableName(tableName)
                .filterExpression("pedidoId = :pedidoId")
                .expressionAttributeValues(
                        Map.of(":pedidoId", AttributeValue.builder().s(pedidoId).build())
                )
                .build();

        return dynamoDbClient.scan(request).items().stream()
                .map(this::toRastreamento)
                .toList();
    }

    private Rastreamento toRastreamento(Map<String, AttributeValue> item) {
        Rastreamento r = new Rastreamento();
        r.setId(item.get("id").s());
        r.setPedidoId(item.get("pedidoId").s());
        r.setStatus(item.get("status").s());
        r.setLocalizacao(item.get("localizacao").s());
        r.setAtualizadoPor(item.get("atualizadoPor").s());
        r.setAtualizadoEm(item.get("atualizadoEm").s());
        return r;
    }
}