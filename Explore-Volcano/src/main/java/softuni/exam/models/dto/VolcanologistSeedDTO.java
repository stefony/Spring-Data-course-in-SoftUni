package softuni.exam.models.dto;
import softuni.exam.util.AdapterUtil;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement(name = "volcanologist")   // В AstronomerSeedDTO си въвеждаме цялата информация това е водещия XML
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistSeedDTO implements Serializable{
    @Size(min = 2,max = 30)
    @XmlElement(name = "first_name")
    private String firstName;

    @Size(min = 2,max = 30)
    @XmlElement(name = "last_name")
    private String lastName;

  @XmlElement(name = "salary")
    @Positive
    private Double salary;

    @Min(18)
    @Max(80)
   @XmlElement(name = "age")
    private Integer age;

    @XmlJavaTypeAdapter(AdapterUtil.class)
    @XmlElement(name = "exploring_from")
    private LocalDate exploringFrom;


    @XmlElement(name = "exploring_volcano_id")
    private Long volcano;

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

    public Long getVolcano() {
        return volcano;
    }

    public void setVolcano(Long volcano) {
        this.volcano = volcano;
    }
}
