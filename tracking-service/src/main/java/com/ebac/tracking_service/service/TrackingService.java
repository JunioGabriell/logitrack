package com.ebac.tracking_service.service;

import com.ebac.tracking_service.dto.RastreamentoRequest;
import com.ebac.tracking_service.dto.RastreamentoResponse;
import com.ebac.tracking_service.model.Rastreamento;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingService {

    private final DynamoDbService dynamoDbService;
    private final SqsService sqsService;

    public TrackingService(DynamoDbService dynamoDbService, SqsService sqsService) {
        this.dynamoDbService = dynamoDbService;
        this.sqsService = sqsService;
    }

    public RastreamentoResponse atualizar(RastreamentoRequest request, String emailUsuario) {
        Rastreamento rastreamento = new Rastreamento(
                request.pedidoId(),
                request.status(),
                request.localizacao(),
                emailUsuario
        );

        dynamoDbService.salvar(rastreamento);

        sqsService.publicar(rastreamento);

        return toResponse(rastreamento);
    }

    public List<RastreamentoResponse> buscarPorPedido(String pedidoId) {
        return dynamoDbService.buscarPorPedidoId(pedidoId).stream()
                .map(this::toResponse)
                .toList();
    }

    private RastreamentoResponse toResponse(Rastreamento r) {
        return new RastreamentoResponse(
                r.getId(),
                r.getPedidoId(),
                r.getStatus(),
                r.getLocalizacao(),
                r.getAtualizadoPor(),
                r.getAtualizadoEm()
        );
    }
}