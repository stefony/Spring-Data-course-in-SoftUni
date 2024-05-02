package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity{

    @Column(nullable = false,unique = true)
//    @Size(min = 3, max = 40)
    private String title;

   @Column(nullable = false)
//    @Size(min = 3, max = 40)
    private String author;


//    @Size(min = 5)
    @Column(nullable = false,columnDefinition = "MEDIUMTEXT") // assuming description can be very long
    private String description;

    @Column(nullable = false)
    private Boolean available;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Genre genre;

    @Column(nullable = false)
    @Positive
    private Double rating;

    @OneToMany(mappedBy = "book")
    private Set<BorrowingRecord> borrowingRecords ;

    // Getters and setters..


    public Book() {
    }

    public Book(String title, String author, String description, Boolean available, Genre genre, Double rating, Set<BorrowingRecord> borrowingRecords) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.available = available;
        this.genre = genre;
        this.rating = rating;
        this.borrowingRecords = borrowingRecords;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Set<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(Set<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }
}
