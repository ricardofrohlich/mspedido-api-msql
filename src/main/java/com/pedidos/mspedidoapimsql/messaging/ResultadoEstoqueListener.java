package com.pedidos.mspedidoapimsql.messaging;

import com.pedidos.mspedidoapimsql.model.StatusPedido;
import com.pedidos.mspedidoapimsql.service.PedidoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ResultadoEstoqueListener {

    private final PedidoService pedidoService;

    public ResultadoEstoqueListener(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_RESULTADO_ESTOQUE)
    public void receber(ResultadoEstoque resultado) {

        System.out.println("Resultado do estoque recebido!");
        System.out.println("Pedido: " + resultado.getPedidoId());
        System.out.println("Produto: " + resultado.getProdutoId());

        if (resultado.isConfirmado()) {

            System.out.println("Estoque confirmado!");

            pedidoService.atualizarStatus(
                    resultado.getPedidoId(),
                    StatusPedido.PROCESSADO
            );

        } else {

            System.out.println("Estoque recusado!");
            System.out.println("Motivo: " + resultado.getMotivo());

            pedidoService.atualizarStatus(
                    resultado.getPedidoId(),
                    StatusPedido.CANCELADO
            );
        }
    }
}
