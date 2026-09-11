package com.pedidos.mspedidoapimsql.repository;

import com.pedidos.mspedidoapimsql.model.Pedido;
import com.pedidos.mspedidoapimsql.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatus(StatusPedido status);
}
//save
//findAll
//findById
//delete