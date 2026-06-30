package com.ebac.tracking_service.service;

import com.ebac.tracking_service.model.Rastreamento;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class SqsService {

    private static final Logger log = LoggerFactory.getLogger(SqsService.class);

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    public SqsService(final SqsClient sqsClient, final ObjectMapper objectMapper) {
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
            log.info("Rastreamento publicado no SQS — ID: {}", rastreamento.getId());

        } catch (Exception e) {
            log.error("Erro ao publicar no SQS: {}", e.getMessage());
            throw new RuntimeException("Erro ao publicar no SQS", e);
        }
    }
}