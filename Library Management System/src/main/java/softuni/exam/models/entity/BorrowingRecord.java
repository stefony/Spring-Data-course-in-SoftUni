package softuni.exam.models.entity;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name ="borrowing_records" )
public class BorrowingRecord extends BaseEntity{

   @Column(name = "borrow_date",nullable = false)
   private LocalDate borrowDate;

    @Column(name = "return_date",nullable = false)
        private LocalDate returnDate;

//    @Size(min = 3, max = 100)
    @Column
    private String remarks;


    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private LibraryMember member;


    public BorrowingRecord() {
    }

    public BorrowingRecord(LocalDate borrowDate, LocalDate returnDate, String remarks, Book book, LibraryMember member) {
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.remarks = remarks;
        this.book = book;
        this.member = member;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LibraryMember getMember() {
        return member;
    }

    public void setMember(LibraryMember member) {
        this.member = member;
    }
}
