package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import softuni.exam.models.dto.ImportConstellationDTO;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;


@Service

public class ConstellationServiceImpl implements ConstellationService {
    private static final String CONSTELLATION_PATH = "src/main/resources/files/json/constellations.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ConstellationRepository constellationRepository;

    public ConstellationServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, ConstellationRepository constellationRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.constellationRepository = constellationRepository;
    }


    @Override
    public boolean areImported() {
        return this.constellationRepository.count()>0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files
                .readString(Path.of(CONSTELLATION_PATH));
    }

    @Override
    public String importConstellations() throws IOException {
        StringBuilder sb = new StringBuilder();
         ImportConstellationDTO[] importConstellationDTOs = this.gson.fromJson(
                 new FileReader((CONSTELLATION_PATH)),ImportConstellationDTO[].class);

         for (ImportConstellationDTO importConstellationDTO:importConstellationDTOs){

             Optional<Constellation> optional = this.constellationRepository.
                     findByName(importConstellationDTO.getName());            // АКО ИМАМЕ ОГРАНИЧЕНИЕ ЗА ПОВТАРЯЩО СЕ ИМЕ ПРАВИМ ТОВА
                                                                                // КАТО В ConstellationRepository СИ ПРАВИМ Optional<Constellation> findByName(String name);
             if (!this.validationUtil.isValid(importConstellationDTO)|| optional.isPresent()){
             sb.append("Invalid constellation\n");
             continue;

             }
             Constellation constellation = this.modelMapper.map(importConstellationDTO,Constellation.class);
             this.constellationRepository.saveAndFlush(constellation);

             sb.append(String.format("Successfully imported constellation %s - %s\n", constellation.getName(),constellation.getDescription()));

         }
        return sb.toString();
    }
}
