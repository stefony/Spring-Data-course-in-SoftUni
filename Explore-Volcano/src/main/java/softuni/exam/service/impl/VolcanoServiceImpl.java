package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportVolcanoDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.enums.VolcanoType;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolcanoServiceImpl implements VolcanoService {
    private static final String VOLCANO_PATH = "src/main/resources/files/json/volcanoes.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    private final VolcanoRepository volcanoRepository;

    private final CountryRepository countryRepository;

    public VolcanoServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, VolcanoRepository volcanoRepository, CountryRepository countryRepository) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.volcanoRepository = volcanoRepository;
        this.countryRepository = countryRepository;

        JsonDeserializer<LocalDate> deserializer = (json, typeOfT, context) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);

        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, deserializer)
                .create();
    }


    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files
                .readString(Path.of(VOLCANO_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {
        StringBuilder sb = new StringBuilder();

        ImportVolcanoDto[] ImportVolcanoDtos = this.gson.fromJson(
                readVolcanoesFileContent(), ImportVolcanoDto[].class);

        for (ImportVolcanoDto importVolcanoDto : ImportVolcanoDtos) {

            Optional<Volcano> optional = this.volcanoRepository.
                    findByName(importVolcanoDto.getName());


            if (!this.validationUtil.isValid(importVolcanoDto) || optional.isPresent()) {
                sb.append("Invalid volcano\n");
                continue;

            }
            Volcano volcano = this.modelMapper.map(importVolcanoDto, Volcano.class);
            volcano.setVolcanoType(importVolcanoDto.getVolcanoType());
            volcano.setCountry(this.countryRepository.findById(importVolcanoDto.getCountry()).get());

            this.volcanoRepository.saveAndFlush(volcano);

            sb.append(String.format("Successfully imported volcano %s of type %s\n", volcano.getName(), volcano.getVolcanoType()));

        }
        return sb.toString();
    }

    @Override
    public String exportVolcanoes() {

        List<Volcano> volcanoes = volcanoRepository.findActiveVolcanoesWithElevationAbove3000AndLastEruption();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return volcanoes.stream()
                .map(v -> String.format(Locale.US, "Volcano: %s\n" +
                                "   *Located in: %s\n" +
                                "   **Elevation: %d\n" +
                                "   ***Last eruption on: %s",
                        v.getName(),
                        v.getCountry().getName(),
                        v.getElevation(),
                        v.getLastEruption() != null ? v.getLastEruption().format(formatter) : "Unknown"))
                .collect(Collectors.joining("\n"));
    }
}
