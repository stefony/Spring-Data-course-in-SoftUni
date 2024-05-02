package softuni.exam.models.entity;
import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "constellations")
public class Constellation extends BaseEntity {


    //    @Size(min = 3,max = 20)
    @Column(nullable = false, unique = true)
    private String name;

    //    @Size(min = 5)
    @Column(nullable = false, length = 255)
    private String description;

    @OneToMany(mappedBy = "constellation")
    private Set<Star> stars;




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

    public Set<Star> getStars() {
        return stars;
    }

    public void setStars(Set<Star> stars) {
        this.stars = stars;
    }
}
