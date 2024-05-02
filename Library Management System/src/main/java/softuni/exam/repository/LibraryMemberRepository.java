package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.service.LibraryMemberService;

import java.util.Optional;

@Repository
public interface LibraryMemberRepository extends JpaRepository<LibraryMember,Long> {

    Optional<LibraryMember> findByPhoneNumber(String phoneNumber);
}
