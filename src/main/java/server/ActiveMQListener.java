package server;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import server.Service.PersonDao;
import server.repos.PersonRepo;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class ActiveMQListener {

    private static final Logger log = Logger.getLogger(GreetingController.class);

    @Autowired
    PersonRepo personRepo;


    @JmsListener(destination = "TestQueue")
    public Message receiveMessage(TextMessage jsonMessage) throws JMSException {
        String messageData = null;
        String dbAdd = "";
        System.out.println("Received message " + jsonMessage);
        log.info(jsonMessage.getJMSCorrelationID());
        log.info(jsonMessage.getText());
        if(jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)jsonMessage;
            messageData = textMessage.getText();
            dbAdd = PersonDao.jsonToDB(messageData, personRepo);
            log.info(dbAdd);
        }
        Destination destination = jsonMessage.getJMSReplyTo();
        jsonMessage.clearProperties();
        jsonMessage.clearBody();
        jsonMessage.setJMSReplyTo(destination);
        jsonMessage.setText(dbAdd);
        return jsonMessage;
    }
}