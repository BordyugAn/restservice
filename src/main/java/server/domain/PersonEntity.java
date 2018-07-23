package server.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PERSON")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "dateofbirth")
    Date dateofbirth;

    @Column(name = "placeofbirth")
    String placeofbirth;

    @Column(name = "location")
    String location;

    public PersonEntity(String name, Date dateofbirth, String placeofbirth, String location) {
        this.name = name;
        this.dateofbirth = dateofbirth;
        this.placeofbirth = placeofbirth;
        this.location = location;
    }

    public PersonEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}