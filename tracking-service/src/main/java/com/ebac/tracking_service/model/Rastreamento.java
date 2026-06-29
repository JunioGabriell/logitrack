package com.ebac.tracking_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Rastreamento {

    private String id;
    private String pedidoId;
    private String status;
    private String localizacao;
    private String atualizadoPor;
    private String atualizadoEm;

    public Rastreamento() {}

    public Rastreamento(String pedidoId, String status,
                        String localizacao, String atualizadoPor) {
        this.id = UUID.randomUUID().toString();
        this.pedidoId = pedidoId;
        this.status = status;
        this.localizacao = localizacao;
        this.atualizadoPor = atualizadoPor;
        this.atualizadoEm = LocalDateTime.now().toString();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPedidoId() { return pedidoId; }
    public void setPedidoId(String pedidoId) { this.pedidoId = pedidoId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public String getAtualizadoPor() { return atualizadoPor; }
    public void setAtualizadoPor(String atualizadoPor) { this.atualizadoPor = atualizadoPor; }

    public String getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(String atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}