package softuni.exam.service.impl;
import softuni.exam.models.entity.Car;
import java.util.Optional;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.dto.CarSeedDTO;
import softuni.exam.dto.ImportCarRootDto;
import softuni.exam.models.entity.CarType;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidationUtil;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

@Service
public class CarsServiceImpl implements CarsService {


    private static final String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";
    private final ModelMapper modelMapper;
    //    private final XmlParser xmlParser;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CarsRepository carsRepository;

    public CarsServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, CarsRepository carsRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.carsRepository = carsRepository;
    }

    @Override
    public boolean areImported() {
        return this.carsRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(CARS_FILE_PATH)));
    }

    @Override
    public String importCars() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(ImportCarRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        ImportCarRootDto importCarRootDto = (ImportCarRootDto) unmarshaller.unmarshal(new File(CARS_FILE_PATH));

        for (CarSeedDTO carSeedDTO : importCarRootDto.getCarSeedDTOS()) {
            // Тук проверяваме по регистрационния номер, а не по марка и модел
            Optional<Car> optionalCar = this.carsRepository.findByPlateNumber(carSeedDTO.getPlateNumber());

            if (!this.validationUtil.isValid(carSeedDTO) || optionalCar.isPresent()) {
                sb.append("Invalid car\n");
                continue;
            }

            Car car = this.modelMapper.map(carSeedDTO, Car.class);
            car.setCarType(CarType.valueOf(carSeedDTO.getCarType()));
            this.carsRepository.saveAndFlush(car);

            sb.append(String.format(Locale.US, "Successfully imported car %s - %s\n", car.getCarMake(), car.getCarModel()));
        }

        return sb.toString();
    }
    }

