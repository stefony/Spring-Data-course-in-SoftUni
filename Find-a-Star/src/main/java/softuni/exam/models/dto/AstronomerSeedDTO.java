package softuni.exam.models.dto;

import softuni.exam.util.AdapterUtil;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name = "astronomer")   // В AstronomerSeedDTO си въвеждаме цялата информация това е водещия XML
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerSeedDTO implements Serializable {


    @Min(500)
    @XmlElement(name = "average_observation_hours")
    private double averageObservationHours;

    @XmlJavaTypeAdapter(AdapterUtil.class)                              // в UTIL СИ ПРАВИМ ADAPTER
    @XmlElement(name = "birthday")
    private LocalDate birthday; // yyyy-MM-dd format handled by JPA

    @Size(min = 2,max = 30)
    @XmlElement(name = "first_name")
    private String firstName;

    @Size(min = 2,max = 30)
    @XmlElement(name = "last_name")
    private String lastName;

    @Min( 15000)
    @XmlElement(name = "salary")
    private double salary;


    @XmlElement(name = "observing_star_id")
    private  long star;                                             // ОТ Astronomers (astronomers.xml) да се вземат полетата за най точно


    // ПРАВИМ СИ ГЕТЕРИ И СЕТЕРИ

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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getAverageObservationHours() {
        return averageObservationHours;
    }

    public void setAverageObservationHours(double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public long getStar() {
        return star;
    }

    public void setStar(long star) {
        this.star = star;
    }
}
