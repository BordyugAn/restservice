package server.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.data.repository.CrudRepository;
import server.domain.PersonEntity;
import server.form.Person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PersonDao {

    private static final Logger log = Logger.getLogger(PersonDao.class);

    public static String jsonToDB(String message, CrudRepository repository){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            Person person = objectMapper.readValue(message, Person.class);
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = format.parse(person.getDateofbirth());
            PersonEntity personEntity = new PersonEntity(person.getName(), date, person.getPlaceofbirth(), person.getLocation());
            repository.save(personEntity);
            log.info("Success add");
            return "Success!";
        }catch(JsonMappingException e){
            log.info("Can't create object");
            log.error(e.getMessage());
            return "Error!";
        } catch (JsonParseException | ParseException e){
            log.info("Can't parse data to JSON");
            log.error(e.getMessage());
            return "Invalid string format!";
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return "Error!";
        }
    }
}
