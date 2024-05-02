package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity{
//    @Size(min = 2, max = 19)
    @Column(name = "part_name",nullable = false,length = 255, unique = true)
    private String partName;

    @Column(nullable = false)
//    @Min(10)
//    @Max(2000)
    private BigDecimal  price;

    @Positive
    @Column(nullable = false)
    private int quantity;

    // Constructors, getters, setters...


    public Part() {
    }

    public Part(String partName, BigDecimal  price, Integer quantity) {
        this.partName = partName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal  price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
