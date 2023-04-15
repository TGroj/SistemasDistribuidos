package com.Central.CentralSolicitudes.Redireccionador;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public class manejaSolicitudes {
    private static final String EXCHANGE = "rabbitmq.exchange";
    private ConnectionFactory cf = new ConnectionFactory();
    private Connection conexion;
    private Channel canal;

    private void exchangeDeclare() throws IOException, TimeoutException {
        conexion = cf.newConnection();
        canal = conexion.createChannel();
        canal.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);
    }

    public Channel declarar(String msg) throws IOException {
        return (Channel) canal.queueDeclare(msg.toString(), false, false, false, null);
    }

    public void enviarSoliUsuario(String msg){

    }


    //consumidor simple
    public String consumirID(){
        AtomicReference<String> id = null;
        try {
            canal.basicConsume("rabbitmq.routing.keySoli", true,
                    (consumerTag, message) -> {
                        String msgBody = new String(message.getBody(), Charset.defaultCharset());
                        id.set(msgBody);
                    },
                    consumerTag -> {
                        System.out.println("Consumidor "+consumerTag+" cancelado");
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return id.get();
    }

}
