package com.proto.demo.LogicaNegocio;
import com.proto.demo.Config.*;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class EnvioSolicitud {
    private RabbitMQConfig rc;
    private int idPaciente;
    private String routingKeySoli = "rabbitmq.routing.keySoli";
    private String exchange = "rabbitmq.exchange";
    private RabbitTemplate rt;

    public EnvioSolicitud(RabbitTemplate rabbitTemplate){
        this.rt = rabbitTemplate;
    }
    public void enviarSolicitud(String msg){
        System.out.print("Mensaje enviado -> %s");
        rt.convertAndSend(exchange, routingKeySoli, msg);
    }

}
