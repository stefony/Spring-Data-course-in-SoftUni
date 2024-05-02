package softuni.exam.dto;

import softuni.exam.models.entity.CarType;
import softuni.exam.models.entity.Task;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement()   // В AstronomerSeedDTO си въвеждаме цялата информация това е водещия XML
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedDTO {
    @Size(min = 2,max = 30)
    @XmlElement(name = "carMake")
    private String carMake;

    @Size(min = 2,max = 30)
    @XmlElement(name = "carModel")
    private String carModel;

    @Positive
    @XmlElement(name = "year")
    private int year;


    @Size(min = 2,max = 30)
    @XmlElement(name = "plateNumber")
    private String plateNumber;

    @Positive
    @XmlElement(name = "kilometers")
    private Integer kilometers;

    @Min(1)
    @XmlElement(name = "engine")
    private double engine;



    @XmlElement(name = "carType")
    private String carType;

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public double getEngine() {
        return engine;
    }

    public void setEngine(double engine) {
        this.engine = engine;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}
