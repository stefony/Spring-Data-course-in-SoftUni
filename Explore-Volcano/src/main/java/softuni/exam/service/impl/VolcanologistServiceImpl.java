package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportVolcanologistRootDto;
import softuni.exam.models.dto.VolcanologistSeedDTO;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
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
public class VolcanologistServiceImpl implements VolcanologistService {
    private static final String VOLCANOLOGISTS_PATH = "src/main/resources/files/xml/volcanologists.xml";
    private final ModelMapper modelMapper;
    //    private final XmlParser xmlParser;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    private final VolcanologistRepository volcanologistRepository;

    private final VolcanoRepository volcanoRepository;

    public VolcanologistServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, VolcanologistRepository volcanologistRepository, VolcanoRepository volcanoRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.volcanologistRepository = volcanologistRepository;
        this.volcanoRepository = volcanoRepository;
    }

    @Override
    public boolean areImported() {
        return this.volcanologistRepository.count()>0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(VOLCANOLOGISTS_PATH)));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext=JAXBContext.newInstance(ImportVolcanologistRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ImportVolcanologistRootDto importVolcanologistRootDto = (ImportVolcanologistRootDto) unmarshaller.unmarshal(new File(VOLCANOLOGISTS_PATH));

        for (VolcanologistSeedDTO volcanologistSeedDTO : importVolcanologistRootDto.getAstronomerSeedDTO()) {

            Optional<Volcanologist> optionalVolcanologist = this.volcanologistRepository.findByFirstNameAndLastName(volcanologistSeedDTO.
                    getFirstName(), volcanologistSeedDTO.getLastName());

            Optional<Volcano> optionalVolcano = this.volcanoRepository.findById(volcanologistSeedDTO.getVolcano());

            if (!this.validationUtil.isValid(volcanologistSeedDTO) || optionalVolcanologist.isPresent() || optionalVolcano.isEmpty()) {
                sb.append("Invalid volcanologist\n");
                continue;
            }

            Volcanologist volcanologist = this.modelMapper.map(volcanologistSeedDTO, Volcanologist.class);

            optionalVolcano.ifPresent(volcanologist::setVolcano);

            this.volcanologistRepository.saveAndFlush(volcanologist);

            sb.append(String.format("Successfully imported volcanologist %s %s\n", volcanologist.getFirstName(), volcanologist.getLastName()));
        }
        return sb.toString();
    }
}