package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "mechanics")
public class Mechanic extends BaseEntity{
//    @Size(min = 2)
    @Column(name = "first_name",nullable = false, unique = true)
    private String firstName;

//    @Size(min = 2)
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

//    @Size(min = 2)
    @Column(nullable = true, unique = true)
    private String phone;

    @OneToMany(mappedBy = "mechanic")
    private List<Task> tasks;

    public Mechanic() {

    }

    public Mechanic(String firstName, String lastName, String email, String phone, List<Task> tasks) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.tasks = tasks;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
