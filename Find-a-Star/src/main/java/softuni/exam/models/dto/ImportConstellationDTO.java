package softuni.exam.models.dto;



import com.google.gson.annotations.Expose;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.io.Serializable;


public class ImportConstellationDTO implements Serializable {  //   това е ВАЖНО!!!!! implements Serializable

    @Expose                                        //   това е ВАЖНО!!!!! Expose
    @Size(min = 3, max = 20)
    private String name;


    @Expose
    @Size(min = 5)
    private String description;



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
}
