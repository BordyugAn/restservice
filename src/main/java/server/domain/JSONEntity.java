package server.domain;


import javax.persistence.*;

@Entity
@Table(name = "JSON")
public class JSONEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "content")
    String content;

    public JSONEntity(String content) {
        this.content = content;
    }

    public JSONEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
