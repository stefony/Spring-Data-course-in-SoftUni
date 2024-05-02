package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.repository.TasksRepository;
import softuni.exam.service.TasksService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TasksServiceImpl implements TasksService {
    private static final String TASKS_FILE_PATH = "src/main/resources/files/xml/tasks.xml";
    private final ModelMapper modelMapper;
    //    private final XmlParser xmlParser;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    private final TasksRepository tasksRepository;

    public TasksServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, TasksRepository tasksRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;

        this.tasksRepository = tasksRepository;
    }

    @Override
    public boolean areImported() {
        return this.tasksRepository.count()>0;
    }

    @Override
    public String readTasksFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(TASKS_FILE_PATH)));
    }

    @Override
    public String importTasks() throws IOException, JAXBException {
        return null;
    }

    @Override
    public String getCoupeCarTasksOrderByPrice() {
        return null;
    }
}
