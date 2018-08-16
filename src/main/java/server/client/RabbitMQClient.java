package server.client;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;

public class RabbitMQClient {
    private BlockingQueue<String> response;
    private String myQueue;
    private String replyQueueName;
    private ConnectionFactory factory;
    private Channel channel;
    private Connection connection;

    public RabbitMQClient(String myQueue, String replyQueueName) throws IOException, TimeoutException {
        response = new ArrayBlockingQueue<String>(1);
        this.myQueue = myQueue;
        this.replyQueueName = replyQueueName;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(myQueue, true, false, false, null);
    }

    public RabbitMQClient(String myQueue) throws IOException, TimeoutException {
        response = new ArrayBlockingQueue<String>(1);
        this.myQueue = myQueue;
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(myQueue, true, false, false, null);
        replyQueueName = channel.queueDeclare().getQueue();
    }

    public String send(String message) throws InterruptedException, IOException {
        String corrId = UUID.randomUUID().toString();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();
        channel.basicPublish("", myQueue, props, message.getBytes());
        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                response.offer(new String(body, "UTF-8"));
            }
        });
        sleep(5000);
        return response.poll();
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }
}
