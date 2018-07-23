package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import server.domain.JSONEntity;
import org.springframework.beans.factory.annotation.Autowired;
import server.domain.PersonEntity;
import server.form.Person;
import server.repos.JSONRepo;
import server.repos.PersonRepo;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    private JSONRepo jsonRepo;

    @Autowired
    private PersonRepo personRepo;

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", required=false, defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/viewtable")
    public List<JSONEntity> viewTable(){
        return (List<JSONEntity>) jsonRepo.findAll();
    }

    @RequestMapping(value = "/addjson", method = RequestMethod.POST)
    public Greeting addJSON(@RequestParam("name") String arg1){
        try{
           Greeting greeting = new Greeting(counter.incrementAndGet(), arg1);
           ObjectMapper objectMapper = new ObjectMapper();
           String content = objectMapper.writeValueAsString(greeting);
           jsonRepo.save(new JSONEntity(content));
           return greeting;
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    @RequestMapping("/choosejson")
    public String chooseJSON(@RequestParam(value="id", required=false) int id) {
        try {
            String content = jsonRepo.findAllById(id).getContent();
            return content;
        }catch (Exception e){
            logger.info(e.getMessage());
            return "Error!!!";
        }
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public String addPerson(@RequestParam("arg") String json)  {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Person person = objectMapper.readValue(json, Person.class);
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = format.parse(person.getDateofbirth());
            personRepo.save(new PersonEntity(person.getName(), date, person.getPlaceofbirth(), person.getLocation()));
            return json;
        } catch (Exception e){
            return "Error!!!";
        }
    }

    @RequestMapping("/person/table")
    public List<PersonEntity> viewPersonTable(){
        return (List<PersonEntity>) personRepo.findAll();
    }


    @RequestMapping("/person/bylocation")
    public List<PersonEntity> viewPersonByLocation(@RequestParam(value="location", required=false) String location){
        return (List<PersonEntity>) personRepo.findAllByLocation(location);
    }

    @RequestMapping("/person/byplace")
    public List<PersonEntity> viewPersonByPlace(@RequestParam(value="place", required=false) String place){
        return (List<PersonEntity>) personRepo.findAllByPlaceofbirth(place);
    }

    @RequestMapping("/person/byname")
    public List<PersonEntity> viewPersonByName(@RequestParam(value="name", required=false) String name){
        return (List<PersonEntity>) personRepo.findAllByName(name);
    }


//    @PutMapping(value = "/person/{id}")
    @RequestMapping(value = "/person/{id}", method = RequestMethod.PUT)
    public String updateStudent(@PathVariable("id") final int id, @Valid @RequestBody String json){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Person person = objectMapper.readValue(json, Person.class);
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = format.parse(person.getDateofbirth());
            PersonEntity personEntity = personRepo.findById(id);
            personEntity.setDateofbirth(date);
            personEntity.setLocation(person.getLocation());
            personEntity.setPlaceofbirth(person.getPlaceofbirth());
            personEntity.setName(person.getName());
            personRepo.save(personEntity);
            return "Success!!";
            //return new ResponseEntity<PersonEntity>(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            //return new ResponseEntity<PersonEntity>(HttpStatus.OK);
            return "Error!!";
        }
    }

    @RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
    public String deletePerson(@PathVariable("id") int id){
        try{
            personRepo.deleteById(id);
            return "Success!!";
        }catch (Exception e){
            return "Error!!";
        }
    }
}
