package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import softuni.exam.models.enums.VolcanoType;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

public class ImportVolcanoDto implements Serializable {
    @Expose
    @Size(min = 2,max = 30)
    private String name;

    @Expose
   @Positive
    private Integer elevation;

    @Expose
    private VolcanoType volcanoType;

    @Expose
    private Boolean isActive;

    @Expose
    private LocalDate lastEruption;

    @Expose
    private Long country;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getElevation() {
        return elevation;
    }

    public void setElevation(Integer elevation) {
        this.elevation = elevation;
    }

    public VolcanoType getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(VolcanoType volcanoType) {
        this.volcanoType = volcanoType;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDate getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(LocalDate lastEruption) {
        this.lastEruption = lastEruption;
    }

    public Long getCountry() {
        return country;
    }

    public void setCountry(Long country) {
        this.country = country;
    }
}
