package softuni.exam.models.dto;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@XmlRootElement(name = "astronomers") //AstronomerRootDto
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerRootDto implements Serializable {

    @XmlElement(name = "astronomer")
   private List<AstronomerSeedDTO> astronomerSeedDTO;

    // getters and setters



    public List<AstronomerSeedDTO> getAstronomerSeedDTO() {
        return astronomerSeedDTO;
    }

    public void setAstronomerSeedDTOs(List<AstronomerSeedDTO> astronomerSeedDTOs) {
    }
}
