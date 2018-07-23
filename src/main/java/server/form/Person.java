package server.form;

public class Person {
    private String name;
    private String dateofbirth;
    private String placeofbirth;
    private String location;

    public Person(String name, String dateofbirth, String placeofbirth, String location) {
        this.name = name;
        this.dateofbirth = dateofbirth;
        this.placeofbirth = placeofbirth;
        this.location = location;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
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
