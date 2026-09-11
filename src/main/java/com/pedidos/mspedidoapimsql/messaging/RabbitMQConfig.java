package com.pedidos.mspedidoapimsql.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "pedidos.exchange";

    public static final String ROUTING_KEY_BAIXAR_ESTOQUE =
            "estoque.baixar";

    public static final String ROUTING_KEY_CONFIRMADO =
            "estoque.confirmado";

    public static final String ROUTING_KEY_RECUSADO =
            "estoque.recusado";

    public static final String QUEUE_RESULTADO_ESTOQUE =
            "pedido.resultado-estoque.queue";


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }


    @Bean
    public Queue queueResultadoEstoque() {
        return new Queue(QUEUE_RESULTADO_ESTOQUE);
    }


    @Bean
    public Binding bindingResultadoConfirmado() {
        return BindingBuilder
                .bind(queueResultadoEstoque())
                .to(exchange())
                .with(ROUTING_KEY_CONFIRMADO);
    }


    @Bean
    public Binding bindingResultadoRecusado() {
        return BindingBuilder
                .bind(queueResultadoEstoque())
                .to(exchange())
                .with(ROUTING_KEY_RECUSADO);
    }
}
