import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.junit.Test;
import server.client.RabbitMQClient;
import server.form.Person;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertTrue;

public class RabbitMQTests {


    private static final Logger log = Logger.getLogger(RabbitMQTests.class);


    @Test
    public void test1() throws IOException, TimeoutException, InterruptedException {
        RabbitMQClient client = new RabbitMQClient("personQueue");
        Person person = new Person("Testoviy chuvak13", "July 18, 2000", "TestPlaceOfBirth", "TestCity");
        ObjectMapper objectMapper = new ObjectMapper();
        String message = objectMapper.writeValueAsString(person);
        String answer = "";
        answer = client.send(message);
        while (answer.equals("")) {

        }
        assertTrue("wrong" , answer.equals("Success!"));
        answer = "";
        answer = client.send("Wrong data");
        while (answer.equals("")) {

        }
        assertTrue(answer.equals("Invalid string format!"));
        client.close();
    }
}
