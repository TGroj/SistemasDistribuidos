package Logica;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProductorSolicitud {
    ConnectionFactory cFactory = new ConnectionFactory();
    String mensaje;
    Channel canal;
    public void enviarSolicitud(String msg) throws IOException, TimeoutException {

        try(Connection conexion = cFactory.newConnection()){
            canal = conexion.createChannel();
            String queueName = msg.toString();
            canal.queueDeclare(queueName, false, false, false, null);
            canal.basicPublish("", queueName, null, msg.getBytes());
        }
    }


}
