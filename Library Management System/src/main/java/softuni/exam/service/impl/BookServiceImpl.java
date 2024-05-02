package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportBookDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.Genre;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private static final String BOOK_PATH = "src/main/resources/files/json/books.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    private final BookRepository bookRepository;

    private final LibraryMemberRepository libraryMemberRepository;

    public BookServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    @Override
    public boolean areImported() {
        return bookRepository.count()>0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
         return new String(Files.readAllBytes(Path.of(BOOK_PATH)));
    }

    @Override
    public String importBooks() throws IOException {

        StringBuilder sb = new StringBuilder();
        ImportBookDTO[] importBookDTOs=this.gson.fromJson(readBooksFromFile(),ImportBookDTO[].class);

        for (ImportBookDTO importBookDTO : importBookDTOs) {

            Optional<Book> optional = this.bookRepository.findByTitle(importBookDTO.getTitle());

            if (!this.validationUtil.isValid(importBookDTO) || optional.isPresent()){
                sb.append("Invalid book\n");
                continue;
            }

            Book book = this.modelMapper.map(importBookDTO,Book.class);
            book.setGenre(Genre.valueOf(importBookDTO.getGenre()));

            this.bookRepository.saveAndFlush(book);
            sb.append(String.format("Successfully imported book %s - %s\n", book.getAuthor(),book.getTitle()));
        }
        return sb.toString();
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.findBookByTitle(title).orElse(null);
    }
}
