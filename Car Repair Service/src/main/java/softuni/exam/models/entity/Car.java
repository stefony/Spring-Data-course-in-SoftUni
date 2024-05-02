package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarType carType;

//    @Size(min = 2,max = 30)
    @Column(name = "car_make",nullable = false)
    private String carMake;

//    @Size(min = 2,max = 30)
    @Column(name = "car_model",nullable = false)
    private String carModel;

    @Positive
    @Column(nullable = false)
    private int year;

//    @Size(min = 2,max = 30)
    @Column(name = "plate_number",nullable = false, unique = true)
    private String plateNumber;

    @Positive
    @Column(nullable = false)
    private Integer kilometers;

//    @Min(1)
    @Column(nullable = false)
    private double engine;

    @OneToMany(mappedBy = "car")
    private List<Task> tasks;

    // Constructors, getters, setters...


    public Car() {

    }

    public Car(CarType carType, String carMake, String carModel, Integer year, String plateNumber, Integer kilometers, double engine, List<Task> tasks) {
        this.carType = carType;
        this.carMake = carMake;
        this.carModel = carModel;
        this.year = year;
        this.plateNumber = plateNumber;
        this.kilometers = kilometers;
        this.engine = engine;
        this.tasks = tasks;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getYear() {
        return year;
    }

    public void setYear(Integer year) {
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
