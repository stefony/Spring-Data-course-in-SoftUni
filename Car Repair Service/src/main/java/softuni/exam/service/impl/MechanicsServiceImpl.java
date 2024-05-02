package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.dto.ImportMechanicsDto;
import softuni.exam.dto.ImportPartDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;

@Service
public class MechanicsServiceImpl implements MechanicsService {

    private static final String MECHANICS_FILE_PATH = "src/main/resources/files/json/mechanics.json";

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    private final MechanicsRepository mechanicsRepository;
    private final TasksRepository tasksRepository;

    public MechanicsServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, MechanicsRepository mechanicsRepository, TasksRepository tasksRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mechanicsRepository = mechanicsRepository;
        this.tasksRepository = tasksRepository;
    }

    @Override
    public boolean areImported() {
        return this.mechanicsRepository.count()>0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(MECHANICS_FILE_PATH)));
    }

    @Override
    public String importMechanics() throws IOException {
        StringBuilder sb = new StringBuilder();



        ImportMechanicsDto[] importMechanicsDtos = this.gson.fromJson(
                readMechanicsFromFile(), ImportMechanicsDto[].class);

        for (ImportMechanicsDto importMechanicsDto  : importMechanicsDtos) {

            Optional<Mechanic> optional = this.mechanicsRepository.
                    findMechanicByFirstNameAndLastName(importMechanicsDto.getFirstName(),importMechanicsDto.getLastName());                      // АКО ИМАМЕ ОГРАНИЧЕНИЕ ЗА ПОВТАРЯЩО СЕ ИМЕ ПРАВИМ ТОВА

            // КАТО В ConstellationRepository СИ ПРАВИМ Optional<Constellation> findByName(String name);
            if (!this.validationUtil.isValid(importMechanicsDto)) {
                sb.append("Invalid mechanic\n");
                continue;
                //Тази проверка използва ValidationUtil за да установи дали обектът
                // importMechanicsDto отговаря на зададените критерии за валидност,
                // които обикновено включват анотации за валидация, приложени към полетата на DTO
            }

            if (optional.isPresent()) {
                sb.append("Invalid mechanic\n");
                continue;
                //Проверка за Съществуващ Запис със Същото Име и Фамилия (optional.isPresent()):
                //Тази проверка използва метод от MechanicsRepository
                // за да намери дали вече съществува механик със същите име и фамилия,
                // които се опитвате да импортирате.
            }
            Optional<Mechanic> existingMechanicByEmail = this.mechanicsRepository.findMechanicByEmail(importMechanicsDto.getEmail());
            if (existingMechanicByEmail.isPresent()) {
                sb.append(String.format("Invalid mechanic\n", importMechanicsDto.getEmail()));
                continue;
                //Проверка за Съществуващ Запис със Същия Email (existingMechanicByEmail.isPresent()):
                //Подобно на втората проверка,
                // тази проверка търси механик със същия електронен адрес в базата данни.
            }
            Mechanic mechanic = this.modelMapper.map(importMechanicsDto, Mechanic.class);
            this.mechanicsRepository.saveAndFlush(mechanic);
            sb.append(String.format(Locale.US, "Successfully imported mechanic %s %s\n", mechanic.getFirstName(), mechanic.getLastName()));

        }
        return sb.toString();
    }
}
