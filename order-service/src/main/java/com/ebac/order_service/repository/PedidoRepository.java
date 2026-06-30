package com.ebac.order_service.repository;

import com.ebac.order_service.entity.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Page<Pedido> findByClienteEmail(String clienteEmail, Pageable pageable);
}