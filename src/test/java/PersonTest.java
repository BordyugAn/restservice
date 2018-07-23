import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Test;
import server.form.Person;

import java.io.IOException;

public class PersonTest {

    @Test
    public void addPersonTest() throws IOException {
        HttpRequest request = HttpRequest.post("http://localhost:8080/person");
        Person person = new Person("Bordyug Anatoly", "July 3, 1995", "Ivanovo","Moscow");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(person);
        request.send("arg="+json);
        //-------------------------------------------------------------
        person.setName("Messi Lionel");
        person.setDateofbirth("June 24, 1987");
        person.setPlaceofbirth("Rosario");
        person.setLocation("Barcelona");
        do{

        }while (request.code()!=200);
        request = HttpRequest.post("http://localhost:8080/person");
        json = objectMapper.writeValueAsString(person);
        request.send("arg="+json);
        //-------------------------------------------------------------
        person.setName("Messi NeLionel");
        person.setDateofbirth("June 13, 1999");
        person.setPlaceofbirth("NeRosario");
        person.setLocation("Barcelona");
        do{

        }while (request.code()!=200);
        request = HttpRequest.post("http://localhost:8080/person");
        json = objectMapper.writeValueAsString(person);
        request.send("arg="+json);
        //-------------------------------------------------------------
        System.out.println(request.code());
        System.out.println(request.body());
    }


    @Test
    public void updatePersonTest() throws JsonProcessingException {
        HttpRequest request = HttpRequest.put("http://localhost:8080/person/1");
        Person person = new Person("Bordyug Anatoly", "July 3, 1995", "Ivanovo","Saint-Petersburg");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(person);
        request.send(json);
        System.out.println(request.code());
        System.out.println(request.body());
    }


    @Test
    public void deletePersonTest(){
        HttpRequest request = HttpRequest.delete("http://localhost:8080/person/2");
        request.send("");
        System.out.println(request.code());
    }

}
