package softuni.exam.models.dto;


import com.google.gson.annotations.Expose;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.StarType;


import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;


public class ImportStarDTO implements Serializable {

    @Expose
    @Size(min = 6)
    private String description;
    @Expose
    @Positive
    private double lightYears;

    @Expose
    @Size(min = 2, max = 30)
    private String name;

    @Expose
    private String starType;
    @Expose
    private long constellation;

// гледаме си жот таблицата Star (stars.json)

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLightYears() {
        return lightYears;
    }

    public void setLightYears(double lightYears) {
        this.lightYears = lightYears;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarType() {
        return starType;
    }

    public void setStarType(String starType) {
        this.starType = starType;
    }

    public long getConstellation() {
        return constellation;
    }

    public void setConstellation(long constellation) {
        this.constellation = constellation;
    }
}
