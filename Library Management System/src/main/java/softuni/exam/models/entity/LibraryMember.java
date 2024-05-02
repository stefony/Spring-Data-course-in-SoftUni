package softuni.exam.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "library_members")
public class LibraryMember extends BaseEntity {



//    @Size(min = 2, max = 30)
    @Column(name = "first_name",nullable = false)
    private String firstName;


//    @Size(min = 2, max = 30)
    @Column(name = "last_name",nullable = false)
    private String lastName;

//    @Size(min = 2, max = 40)
    @Column
    private String address;


//    @Size(min = 2, max = 20)
    @Column(name = "phone_number",nullable = false,unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "member")
    private Set<BorrowingRecord> borrowingRecords;

    // Getters and setters...


    public LibraryMember() {
    }

    public LibraryMember(String firstName, String lastName, String address, String phoneNumber, Set<BorrowingRecord> borrowingRecords) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.borrowingRecords = borrowingRecords;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(Set<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }
}
