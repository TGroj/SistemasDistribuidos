package App;
import com.google.gson.Gson;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class CLIDoctor {
    private static final String EXCHANGE = "seguroSocial";
    public static void main(String[] args) {
        ConnectionFactory cFactory = new ConnectionFactory();
        String pacienteId;
        String doctorId;
        Scanner escaner = new Scanner(System.in);
        System.out.println("introduzca su No. de Control");
        doctorId = escaner.nextLine();
        try(Connection conexion = cFactory.newConnection();
            Channel canal = conexion.createChannel()){

            canal.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);
            System.out.println("Introduzca id de paciente para enviar solicitud...");
            pacienteId = escaner.nextLine();
            String routingKey = pacienteId;
            jsonCuerpoMsg msg = new jsonCuerpoMsg(doctorId, pacienteId);
            System.out.println("produciendo solicitud ");
            //Gson msg = msgToJson(doctorId, pacienteId);                        //pacienteId.getBytes()
            canal.basicPublish(EXCHANGE, routingKey, null, msg.toString().getBytes());
            System.out.println("Esperando respuesta");
            int conteo = 1;
            expedienteEntrante();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    public static final void expedienteEntrante()
            throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection conexion = connectionFactory.newConnection();
        Channel channel = conexion.createChannel();
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);
        String queueName = channel.queueDeclare().getQueue();
        String routingKey = "Doc";
        channel.queueBind(queueName, EXCHANGE, routingKey);
        channel.basicConsume(queueName, true, (consumerTag, message) -> {
                   String msgBody = new String(message.getBody(), Charset.defaultCharset());
                   System.out.println("Expediente: " + msgBody.toString());
                },
                consumerTag -> {
                    System.out.println("Error");
                });
    }
}
