package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportLibraryMemberDto;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {
    private static final String LIBRARY_MEMBER_PATH = "src/main/resources/files/json/library-members.json";
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberServiceImpl(ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, LibraryMemberRepository libraryMemberRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    @Override
    public boolean areImported() {
        return libraryMemberRepository.count()>0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(LIBRARY_MEMBER_PATH)));
    }

    @Override
    public String importLibraryMembers() throws IOException {
        StringBuilder sb = new StringBuilder();
        ImportLibraryMemberDto[]importLibraryMemberDtos = this.gson.fromJson(readLibraryMembersFileContent(),ImportLibraryMemberDto[].class);

        for (ImportLibraryMemberDto importLibraryMemberDto:importLibraryMemberDtos){
            Optional<LibraryMember> optional= this.libraryMemberRepository.findByPhoneNumber(importLibraryMemberDto.getPhoneNumber());

            if (optional.isPresent()) {                     // Проверка за съществуващ телефонен номер в базата данни
                sb.append("Invalid library member\n");
                continue;
            }

            if (!this.validationUtil.isValid(importLibraryMemberDto)){   //Проверка за валидност на DTO
                sb.append("Invalid library member\n");
                continue;
            }

            LibraryMember libraryMember = this.modelMapper.map(importLibraryMemberDto,LibraryMember.class); // Мапинг и проверка за null стойности
            if (libraryMember.getFirstName() == null || libraryMember.getLastName() == null) {
                sb.append("Invalid library member\n");
                continue;
            }

            this.libraryMemberRepository.saveAndFlush(libraryMember);
            sb.append(String.format("Successfully imported library member %s - %s\n", libraryMember.getFirstName(),libraryMember.getLastName()));
        }
        return sb.toString();
    }

    @Override
    public LibraryMember findMemberById(Long id) {
        return libraryMemberRepository.findById(id).orElse(null);
    }


    
}
