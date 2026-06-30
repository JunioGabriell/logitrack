package com.ebac.delivery_service.repository;

import com.ebac.delivery_service.model.Entrega;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EntregaRepository extends MongoRepository<Entrega, String> {

    Page<Entrega> findByDriverEmail(String driverEmail, Pageable pageable);
    Optional<Entrega> findByPedidoId(Long pedidoId);
}