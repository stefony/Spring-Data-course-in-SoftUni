package softuni.exam.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

public class ImportPartDto implements Serializable {
    @Expose
    private String partName;

    @Expose
    @Min(10)
    @Max(2000)
    private BigDecimal price;
    @Expose
       @Positive
       private Integer quantity;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    // Constructors, getters, setters...
}
