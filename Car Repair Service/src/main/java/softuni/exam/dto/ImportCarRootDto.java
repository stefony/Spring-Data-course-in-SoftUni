package softuni.exam.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars") //AstronomerRootDto
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportCarRootDto {


    @XmlElement(name = "car")
    private List<CarSeedDTO> carSeedDTOS;

    // getters and setters


    public List<CarSeedDTO> getCarSeedDTOS() {
        return carSeedDTOS;
    }

    public void setCarSeedDTOS(List<CarSeedDTO> carSeedDTOS) {
        this.carSeedDTOS = carSeedDTOS;
    }
}
