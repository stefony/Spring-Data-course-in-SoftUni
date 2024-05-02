package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import softuni.exam.models.entity.Volcano;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

public class ImportCountryDto implements Serializable {

    @Expose
    @Size(min = 3,max = 30)
   private String name;

    @Expose
    @Size(min = 3,max = 30)
    private String capital;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
