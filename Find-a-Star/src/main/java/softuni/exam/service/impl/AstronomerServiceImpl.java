package softuni.exam.service.impl;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerRootDto;
import softuni.exam.models.dto.AstronomerSeedDTO;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidationUtil;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    private static final String ASTRONOMER_PATH = "src/main/resources/files/xml/astronomers.xml";
    private final ModelMapper modelMapper;
//    private final XmlParser xmlParser;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final AstronomerRepository astronomerRepository;
    private final StarRepository starRepository; //
// ДОБАВЯМЕ "StarRepository starRepository" ЗАЩОТО ТАБЛИЦАТА astronomers table has a relation with stars table

    public AstronomerServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, AstronomerRepository astronomerRepository, StarRepository starRepository) {

        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
    }


    @Override
    public boolean areImported() {
        return this.astronomerRepository.count()>0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(ASTRONOMER_PATH)));

    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder(); //еднакво

        JAXBContext jaxbContext=JAXBContext.newInstance(AstronomerRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        AstronomerRootDto astronomerRootDto = (AstronomerRootDto) unmarshaller.unmarshal(new File(ASTRONOMER_PATH));

        for (AstronomerSeedDTO astronomerSeedDTO : astronomerRootDto.getAstronomerSeedDTO()) {

            Optional<Astronomer> optionalAstronomer = this.astronomerRepository.findByFirstNameAndLastName(astronomerSeedDTO.getFirstName(),astronomerSeedDTO.getLastName());


            Optional<Star> optionalStar = this.starRepository.findById(astronomerSeedDTO.getStar());

            if (!this.validationUtil.isValid(astronomerSeedDTO) || optionalAstronomer.isPresent()
            || optionalStar.isEmpty()){



                sb.append("Invalid astronomer\n");
                continue;
            }
            Astronomer astronomer = this.modelMapper.map(astronomerSeedDTO,Astronomer.class);

            astronomer.setObservingStar(optionalStar.get());
            this.astronomerRepository.saveAndFlush(astronomer);
            sb.append(String.format(Locale.US,"Successfully imported astronomer %s %s - %.2f\n",astronomer.getFirstName()
                    ,astronomer.getLastName(),astronomer.getAverageObservationHours()));
        }
        return sb.toString();                   //еднакво


    }
}
