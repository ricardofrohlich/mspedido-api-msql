package com.pedidos.mspedidoapimsql.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EstoquePublisher {

    private final RabbitTemplate rabbitTemplate;

    public EstoquePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarBaixaEstoque(BaixarEstoqueCommand comando) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_BAIXAR_ESTOQUE,
                comando
        );
    }
}