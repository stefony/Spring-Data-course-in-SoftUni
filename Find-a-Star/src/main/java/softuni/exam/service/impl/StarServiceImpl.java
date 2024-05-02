package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportStarDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StarServiceImpl implements StarService {
    private static final String STAR_PATH = "src/main/resources/files/json/stars.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    private final StarRepository starRepository;

    private final ConstellationRepository constellationRepository;  // добавяме то защото го имаме в ImportStarDTO  private long constellation;

    public StarServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, StarRepository starRepository, ConstellationRepository constellationRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.starRepository = starRepository;
        this.constellationRepository = constellationRepository;
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count()>0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files
                .readString(Path.of(STAR_PATH));
    }

    @Override
    public String importStars() throws IOException {

        StringBuilder sb = new StringBuilder();
        ImportStarDTO[] ImportStarDTOs = this.gson.fromJson(
               readStarsFileContent(),ImportStarDTO[].class);

        for (ImportStarDTO importStarDTO :ImportStarDTOs ){

            Optional<Star> optional = this.starRepository.
                    findByName(importStarDTO.getName());                      // АКО ИМАМЕ ОГРАНИЧЕНИЕ ЗА ПОВТАРЯЩО СЕ ИМЕ ПРАВИМ ТОВА

                                                                                // КАТО В ConstellationRepository СИ ПРАВИМ Optional<Constellation> findByName(String name);
            if (!this.validationUtil.isValid(importStarDTO) || optional.isPresent()){
                sb.append("Invalid star\n");
                continue;

            }
            Star star = this.modelMapper.map(importStarDTO,Star.class);
            star.setStarType(StarType.valueOf(importStarDTO.getStarType()));   ////ТОВА Е НОВО!!!! ЗАЩОТО  ИМАМЕ  В  ImportStarDTO     private String starType;
            star.setConstellation(this.constellationRepository.findById(importStarDTO.getConstellation()).get()); // ТОВА Е НОВО!! ЗАЩОТО  ИМАМЕ  В  ImportStarDTO private long constellation;

            this.starRepository.saveAndFlush(star);

            sb.append(String.format("Successfully imported star %s - %.2f light years\n", star.getName(),star.getLightYears()));

        }
        return sb.toString();
    }


    @Override
    public String exportStars() {
       return this.starRepository.findAllByStarTypeOrderByLightYears()
                .stream().map(s -> String.format(Locale.US,"Star: %s\n" +
                        "   *Distance: %.2f light years\n" +
                        "   **Description: %s\n" +
                        "   ***Constellation: %s\n", s.getName(), s.getLightYears(), s.getDescription(),
                        s.getName())).collect(Collectors.joining());

    }
}
