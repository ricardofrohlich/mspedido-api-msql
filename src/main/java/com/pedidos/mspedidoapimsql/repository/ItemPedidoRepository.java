package com.pedidos.mspedidoapimsql.repository;

import com.pedidos.mspedidoapimsql.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    @Query("SELECT COALESCE(SUM(i.subtotal), 0) FROM ItemPedido i WHERE i.pedido.id = :pedidoId")
    Double somarSubtotalPorPedido(@Param("pedidoId") Long pedidoId);
}
