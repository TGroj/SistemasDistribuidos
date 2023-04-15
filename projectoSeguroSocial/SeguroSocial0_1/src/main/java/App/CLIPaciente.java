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

public class CLIPaciente {
    private static final String EXCHANGE = "seguroSocial";
    public static void main(String[] args) {
        String routingKeyEnvio;
        ConnectionFactory cFactory = new ConnectionFactory();
        Scanner escaner = new Scanner(System.in);

        //creacion Expediente
        UsuarioExpediente ue = new UsuarioExpediente("juanito perez", "O+", "Deportiva");
        try {
            Connection conexion = cFactory.newConnection();
            Channel canal = conexion.createChannel();
            canal.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);

            String queueName = canal.queueDeclare().getQueue();
            String routingKey = "01";

            canal.queueBind(queueName, EXCHANGE, routingKey);
            canal.basicConsume(queueName, true, (consumerTag, msg) -> {
                String msgBody = new String(msg.getBody(), Charset.defaultCharset());
                if (msgBody.contains("01")){
                    System.out.println("solicitud de acceso a expediente");
                    String opcion = escaner.nextLine();
                        try {
                            if (opcion.equalsIgnoreCase("si")){
                                enviarExpediente(ue);
                            }else{
                                msgAccesoDenegado();
                            }
                        } catch (TimeoutException e) {
                            throw new RuntimeException(e);
                        }

                }else{
                    System.out.println("equivocado");
                }
            },
            consumerTag -> {
                System.out.println("Consumidor "+consumerTag+" cancelado");
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    //envia expediente a consumidor del Doctor
    public static final void enviarExpediente(UsuarioExpediente user) throws IOException, TimeoutException {
        ConnectionFactory cFactory = new ConnectionFactory();
        try(Connection conexion = cFactory.newConnection()) {
            Channel canal = conexion.createChannel();
            String queueName = "Doc";
            canal.queueDeclare(queueName, false, false, false, null);
            canal.basicPublish(EXCHANGE, queueName, null, user.toString().getBytes());
        }
    }

    public static final void msgAccesoDenegado(){
        ConnectionFactory cFactory = new ConnectionFactory();
        try(Connection conexion = cFactory.newConnection()) {
            Channel canal = conexion.createChannel();
            String queueName = "Doc";
            canal.queueDeclare(queueName, false, false, false, null);
            canal.basicPublish(EXCHANGE, queueName, null, "acceso denegado".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }


}
