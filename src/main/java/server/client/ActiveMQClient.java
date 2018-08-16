package server.client;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import javax.jms.*;
import java.util.UUID;

public class ActiveMQClient implements MessageListener {

    private static final Logger log = Logger.getLogger(ActiveMQClient.class);
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private String queueName;
    private long start, end;

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;
    private Destination replyDestination;
    private MessageConsumer responseConsumer;

    public ActiveMQClient(String queueName) throws JMSException {
        this.queueName = queueName;
        connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(this.queueName);
        producer = session.createProducer(destination);
        replyDestination = session.createTemporaryQueue();
        responseConsumer = session.createConsumer(replyDestination);
        responseConsumer.setMessageListener(this);
    }

    public void sendMessage(String sendMessage) throws JMSException {
        TextMessage message = session.createTextMessage(sendMessage);
        String corrId = UUID.randomUUID().toString();
        message.setJMSCorrelationID(corrId);
        message.setJMSReplyTo(replyDestination);
        start = System.currentTimeMillis();
        producer.send(message);
    }

    public void close() throws JMSException {
        connection.close();
    }

    @Override
    public void onMessage(Message message) {
        String messageText = null;
        end = System.currentTimeMillis();
        try {
                TextMessage textMessage = (TextMessage) message;
                messageText = textMessage.getText();
                log.info(message.getJMSCorrelationID());
                log.info(((TextMessage) message).getText());
                log.info(message.getJMSTimestamp());
                System.out.println("messageText = " + messageText);
                System.out.println(end - start);
        } catch (JMSException e) {
            //Handle the exception appropriately
        }
    }
}
