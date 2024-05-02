package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCountryDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {
    private static final String COUNTRY_PATH = "src/main/resources/files/json/countries.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    private final CountryRepository countryRepository;

    public CountryServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, CountryRepository countryRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count()>0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files
                .readString(Path.of(COUNTRY_PATH));
    }

    @Override
    public String importCountries() throws IOException {

        StringBuilder sb = new StringBuilder();
        ImportCountryDto[] importCountryDtos = this.gson.fromJson(
                new FileReader((COUNTRY_PATH)),ImportCountryDto[].class);

        for (ImportCountryDto importCountryDto:importCountryDtos){

            Optional<Country> optional = this.countryRepository.
                    findByName(importCountryDto.getName());
            if (!this.validationUtil.isValid(importCountryDto)|| optional.isPresent()){
                sb.append("Invalid country\n");
                continue;

            }
            Country country = this.modelMapper.map(importCountryDto,Country.class);
            this.countryRepository.saveAndFlush(country);

            sb.append(String.format("Successfully imported country %s - %s\n", country.getName(),country.getCapital()));

        }
        return sb.toString();
    }
}
