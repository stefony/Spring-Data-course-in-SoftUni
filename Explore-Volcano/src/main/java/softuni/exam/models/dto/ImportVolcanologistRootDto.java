package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "volcanologists") //AstronomerRootDto
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportVolcanologistRootDto implements Serializable {
    @XmlElement(name = "volcanologist")
    private List<VolcanologistSeedDTO> astronomerSeedDTO;

    public List<VolcanologistSeedDTO> getAstronomerSeedDTO() {
        return astronomerSeedDTO;
    }

    public void setAstronomerSeedDTO(List<VolcanologistSeedDTO> astronomerSeedDTO) {
        this.astronomerSeedDTO = astronomerSeedDTO;
    }
}
