import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import server.client.ActiveMQClient;
import server.form.Person;

import javax.jms.JMSException;

import static java.lang.Thread.sleep;

public class ActiveMQTests {

    @Test
    public void createQueueTest() throws JMSException, JsonProcessingException, InterruptedException {
        ActiveMQClient client = new ActiveMQClient("TestQueue");
        Person person = new Person("parapapa", "July 18, 2000", "TestPlaceOfBirth", "TestCity");
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(person);
        client.sendMessage(message);
        sleep(5000);
        client.close();
    }
}
