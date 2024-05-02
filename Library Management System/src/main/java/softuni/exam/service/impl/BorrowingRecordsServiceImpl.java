package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BorrowingRecordsRootDto;
import softuni.exam.models.dto.BorrowingRecordsSeedDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.Genre;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.service.BookService;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {

    private static final String BORROWING_RECORDS_PATH = "src/main/resources/files/xml/borrowing-records.xml";
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final XmlParser xmlParser;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final LibraryMemberService libraryMemberService;
    private final BookService bookService;

    public BorrowingRecordsServiceImpl(ModelMapper modelMapper, ValidationUtil validationUtil, BorrowingRecordRepository borrowingRecordRepository, XmlParser xmlParser, Gson gson, LibraryMemberService libraryMemberService, BookService bookService) {
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.xmlParser = xmlParser;
        this.gson = gson;
        this.libraryMemberService = libraryMemberService;
        this.bookService = bookService;
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count()>0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(BORROWING_RECORDS_PATH)));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(BORROWING_RECORDS_PATH, BorrowingRecordsRootDto.class)
                .getBorrowingRecordsSeedDtos()
                .stream()
                .filter(borrowingRecordsSeedDto -> {
                    boolean isValid = validationUtil.isValid(borrowingRecordsSeedDto);

                    LibraryMember libraryMember = libraryMemberService.findMemberById(borrowingRecordsSeedDto.getMember().getId());
                    if (libraryMember == null) {
                        isValid = false;
                    }

                    Book bookByTitle = bookService.getBookByTitle(borrowingRecordsSeedDto.getBook().getTitle());

                    if (bookByTitle == null) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format("Successfully imported borrowing record %s - %s",
                                    borrowingRecordsSeedDto.getBook().getTitle(),
                                    borrowingRecordsSeedDto.getBorrowDate())
                                    : "Invalid borrowing record")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(borrowingRecordDto -> {
                    BorrowingRecord borrowingRecord = modelMapper.map(borrowingRecordDto, BorrowingRecord.class);

                    LibraryMember libraryMember = libraryMemberService.findMemberById(borrowingRecordDto.getMember().getId());
                    Book bookByTitle = bookService.getBookByTitle(borrowingRecordDto.getBook().getTitle());

                    borrowingRecord.setBook(bookByTitle);
                    borrowingRecord.setMember(libraryMember);

                    return borrowingRecord;
                })
                .forEach(borrowingRecordRepository::save);


        return sb.toString();
    }



    @Override
    public String exportBorrowingRecords() {
        Set<BorrowingRecord> allByReturnDateIsNull = borrowingRecordRepository
                .findAllByBorrowDateBeforeAndBook_GenreOrderByBorrowDateDesc(LocalDate.parse("2021-09-10"), Genre.SCIENCE_FICTION);

        StringBuilder stringBuilder = new StringBuilder();

        allByReturnDateIsNull.forEach(borrowRecord -> {
            stringBuilder.append(String.format("Book title: %s\n" +
                                    "*Book author: %s\n" +
                                    "**Date borrowed: %s\n" +
                                    "***Borrowed by: %s %s",
                            borrowRecord.getBook().getTitle(),
                            borrowRecord.getBook().getAuthor(),
                            borrowRecord.getBorrowDate().toString(),
                            borrowRecord.getMember().getFirstName(),
                            borrowRecord.getMember().getLastName()))
                    .append(System.lineSeparator());
        });

        return stringBuilder.toString().trim();
    }
}
