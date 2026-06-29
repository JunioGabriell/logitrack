package com.ebac.order_service.repository;

import com.ebac.order_service.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteEmail(String clienteEmail);
}