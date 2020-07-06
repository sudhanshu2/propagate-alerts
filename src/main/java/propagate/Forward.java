package propagate;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;

public class Forward {

    Forward(boolean forward) {
        String FORWARD_QUEUE;
        if (forward) {
           FORWARD_QUEUE = "alert";
        } else {
            FORWARD_QUEUE = "alive";
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(FORWARD_QUEUE, false, false, false, null);
            channel.basicPublish("", FORWARD_QUEUE, null, FORWARD_QUEUE.getBytes(StandardCharsets.UTF_8));
            System.out.println("Sent " + FORWARD_QUEUE);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
   }
}
