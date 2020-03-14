package pl.coderslab.charity.entities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "institution")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;
    private String description;

    private Date created;
    private Date updated;

    public Institution() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @PrePersist
    public void setCreated() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        this.created = new Date(calendar.getTime().getTime());
    }

    @PreUpdate
    public void setUpdated() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        this.created = new Date(calendar.getTime().getTime());
    }

    @Override
    public String toString() {
        return "Institution{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
