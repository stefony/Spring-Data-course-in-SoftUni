package softuni.exam.models.entity;
import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name = "astronomers")
public class Astronomer extends BaseEntity{



//@Size(min = 2,max = 30)
    @Column(name = "first_name",nullable = false)
    private String firstName;

//   @Size(min = 2,max = 30)
     @Column(name = "last_name",nullable = false)
    private String lastName;

//    @Min( 15000)
    @Column(nullable = false)
    private double salary;

//    @Min(500)
    @Column(name = "average_observation_hours",nullable = false)
    private double averageObservationHours;

    @Column
    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "observing_star_id")
    private Star observingStar;


    public Astronomer() {
    }

    public Astronomer(String firstName, String lastName, Double salary, Double averageObservationHours, LocalDate birthday, Star observingStar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.averageObservationHours = averageObservationHours;
        this.birthday = birthday;
        this.observingStar = observingStar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getAverageObservationHours() {
        return averageObservationHours;
    }

    public void setAverageObservationHours(Double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Star getObservingStar() {
        return observingStar;
    }

    public void setObservingStar(Star observingStar) {
        this.observingStar = observingStar;
    }
}
