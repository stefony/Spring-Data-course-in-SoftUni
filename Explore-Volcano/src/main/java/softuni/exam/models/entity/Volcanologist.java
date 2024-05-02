package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "volcanologists")
public class Volcanologist extends BaseEntity{

    //@Size(min = 2,max = 30)
    @Column(name = "first_name", nullable = false, unique = true)
    private String firstName;

    //@Size(min = 2,max = 30)
    @Column(name = "last_name", nullable = false, unique = true)
    private String lastName;

    @Column(nullable = false)
    //@Positive
    private Double salary;

//    @Min(18)
//    @Max(80)
    @Column(nullable = false)
    private Integer age;

    @Column(name = "exploring_from")
    private LocalDate exploringFrom;

    @ManyToOne
    @JoinColumn(name = "exploring_volcano_id")
    private Volcano volcano;

    public Volcanologist() {
    }

    public Volcanologist(String firstName, String lastName, Double salary, Integer age, LocalDate exploringFrom, Volcano volcano) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.age = age;
        this.exploringFrom = exploringFrom;
        this.volcano = volcano;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getExploringFrom() {
        return exploringFrom;
    }

    public void setExploringFrom(LocalDate exploringFrom) {
        this.exploringFrom = exploringFrom;
    }

    public Volcano getVolcano() {
        return volcano;
    }

    public void setVolcano(Volcano volcano) {
        this.volcano = volcano;
    }
}
