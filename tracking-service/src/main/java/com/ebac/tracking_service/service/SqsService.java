package com.ebac.tracking_service.service;

import com.ebac.tracking_service.model.Rastreamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;

@Service
public class SqsService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    public SqsService(SqsClient sqsClient, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    public void publicar(Rastreamento rastreamento) {
        try {
            String mensagem = objectMapper.writeValueAsString(rastreamento);

            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(mensagem)
                    .build();

            sqsClient.sendMessage(request);
            System.out.println("✅ Rastreamento publicado no SQS: " + rastreamento.getId());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao publicar no SQS", e);
        }
    }

    public List<Rastreamento> lerMensagens() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10)
                .build();

        return sqsClient.receiveMessage(request).messages().stream()
                .map(msg -> {
                    try {
                        return objectMapper.readValue(msg.body(), Rastreamento.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Erro ao ler mensagem do SQS", e);
                    }
                })
                .toList();
    }
}