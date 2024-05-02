package softuni.exam.models.dto;

import softuni.exam.util.AdapterUtil;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;

@XmlRootElement                                    // В AstronomerSeedDTO си въвеждаме цялата информация
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordsSeedDto  implements Serializable {
    @XmlJavaTypeAdapter(AdapterUtil.class)
    @XmlElement(name = "borrow-date")
    private LocalDate borrowDate;

    @XmlJavaTypeAdapter(AdapterUtil.class)
    @XmlElement(name = "return-date")
    private LocalDate returnDate;

    @XmlElement(name = "book")
    private BookDto book;

    @XmlElement(name = "member")
    private LibraryMemberDto member;

    @XmlElement(name = "remarks")
    private String remarks;


 @NotNull
    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public BorrowingRecordsSeedDto setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
        return this;
    }

  @NotNull
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
 @NotNull
    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }
 @NotNull
    public LibraryMemberDto getMember() {
        return member;
    }

    public void setMember(LibraryMemberDto member) {
        this.member = member;
    }
 @NotNull
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
