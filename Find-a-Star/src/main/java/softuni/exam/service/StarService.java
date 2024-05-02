package softuni.exam.service;

import org.springframework.stereotype.Service;

import java.io.IOException;



public interface StarService {

    boolean areImported();

    String readStarsFileContent() throws IOException;
	
	String importStars() throws IOException;

    String exportStars();
}
