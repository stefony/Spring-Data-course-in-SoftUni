package softuni.exam.dto;

import com.google.gson.annotations.Expose;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;


public class ImportMechanicsDto implements Serializable {

    @Expose
    private String email;
    @Expose
    @Size(min = 2)
    private String firstName;
    @Expose
    @Size(min = 2)
    private String lastName;
    @Expose
    @Size(min = 2)
    private String phone;


    public ImportMechanicsDto() {
    }

    public ImportMechanicsDto(String email, String firstName, String lastName, String phone, long tasks) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.tasks = tasks;
    }

    private long tasks;


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

    public long getTasks() {
        return tasks;
    }

    public void setTasks(long tasks) {
        this.tasks = tasks;
    }
}
