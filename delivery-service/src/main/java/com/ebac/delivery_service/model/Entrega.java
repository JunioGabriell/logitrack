package com.ebac.delivery_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "entregas")
public class Entrega {

    @Id
    private String id;
    private Long pedidoId;
    private String clienteEmail;
    private String descricao;
    private String endereco;
    private String driverEmail;
    private Status status;

    private LocalDateTime criadoEm;
    private LocalDateTime atualizadoEm;

    public enum Status {
        AGUARDANDO, ATRIBUIDO, EM_TRANSITO, ENTREGUE, CANCELADO
    }

    public Entrega() {}

    public Entrega(Long pedidoId, String clienteEmail, String descricao, String endereco) {
        this.pedidoId = pedidoId;
        this.clienteEmail = clienteEmail;
        this.descricao = descricao;
        this.endereco = endereco;
        this.status = Status.AGUARDANDO;
        this.criadoEm = LocalDateTime.now();
        this.atualizadoEm = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getDriverEmail() { return driverEmail; }
    public void setDriverEmail(String driverEmail) { this.driverEmail = driverEmail; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCriadoEm() { return criadoEm; }
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}