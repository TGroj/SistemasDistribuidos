package com.proto.demo.Config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

public class RabbitMQConfig {
    private final String queueSolicitud = "rabbitmq.queue.solicitud";
    private final String exchange = "rabbitmq.exchange";
    /*private final String routingKeySoli = "rabbitmq.routing.keySoli";
    private final String routingKeyJson = "rabbitmq.routing.keyJson";
    */
    public Queue queue(){
        return new Queue(queueSolicitud);
    }
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    /*public Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKeySoli);
    }
    */

}
