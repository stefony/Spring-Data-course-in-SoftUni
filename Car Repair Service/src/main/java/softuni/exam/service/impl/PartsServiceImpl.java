package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.dto.ImportPartDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;

@Service
public class PartsServiceImpl implements PartsService {
    private static final String PARTS_FILE_PATH = "src/main/resources/files/json/parts.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    private final PartsRepository partsRepository;

    public PartsServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, PartsRepository partsRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;

        this.partsRepository = partsRepository;
    }

    @Override
    public boolean areImported() {
        return this.partsRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(PARTS_FILE_PATH)));
    }

    @Override
    public String importParts() throws IOException {

        StringBuilder sb = new StringBuilder();



        ImportPartDto[] importPartDtos = this.gson.fromJson(
                readPartsFileContent(), ImportPartDto[].class);

        for (ImportPartDto importPartDto  : importPartDtos) {

            Optional<Part> optional = this.partsRepository.
                    findByPartName(importPartDto.getPartName());                      // АКО ИМАМЕ ОГРАНИЧЕНИЕ ЗА ПОВТАРЯЩО СЕ ИМЕ ПРАВИМ ТОВА

            // КАТО В ConstellationRepository СИ ПРАВИМ Optional<Constellation> findByName(String name);
            if (!this.validationUtil.isValid(importPartDto) || optional.isPresent()) {
                sb.append("Invalid part\n");
                continue;

                //Проверка за Валидност с validationUtil.isValid(importPartDto):
                //Използва ValidationUtil за да определи дали даден importPartDto
                // (DTO обект за част) е валиден според критериите за валидация,
                // зададени чрез анотации в DTO класа.

                //Проверка за Съществуващ Запис (optional.isPresent()):
                //Тази проверка търси в базата данни за съществуваща част със същото
                // уникално идентификаторно поле (например, име на част),
                // което се опитвате да импортирате. optional е обект от тип Optional,
                // който е върнат от метода на репозитория, извършващ търсенето

            }
            Part part = this.modelMapper.map(importPartDto, Part.class);
            this.partsRepository.saveAndFlush(part);

            sb.append(String.format(Locale.US,"Successfully imported part %s - %.2f\n", part.getPartName(), part.getPrice()));


        }
        return sb.toString();
    }
}
