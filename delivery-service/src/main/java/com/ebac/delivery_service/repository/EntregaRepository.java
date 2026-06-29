package com.ebac.delivery_service.repository;

import com.ebac.delivery_service.model.Entrega;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EntregaRepository extends MongoRepository<Entrega, String> {

    List<Entrega> findByDriverEmail(String driverEmail);

    Optional<Entrega> findByPedidoId(Long pedidoId);
}