package App;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeoutException;

public class ConsumeExpediente {
    private static final String EXCHANGE = "seguroSocial";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection conexion = connectionFactory.newConnection();
        Channel canal = conexion.createChannel();
        canal.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);
        String queueName = canal.queueDeclare().getQueue();
        String routingKey = "Doc";
        canal.queueBind(queueName, EXCHANGE, routingKey);
        canal.basicConsume(queueName, true, (consumerTag, message) -> {
            String msgBody = new String(message.getBody(), Charset.defaultCharset());
                    System.out.println("Expediente: " + msgBody.toString());
        },
        consumerTag -> {
            System.out.println("Error");
        });
    }
}
