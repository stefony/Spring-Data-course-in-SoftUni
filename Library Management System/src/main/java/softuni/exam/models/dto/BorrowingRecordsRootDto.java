package softuni.exam.models.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingRecordsRootDto {

    @XmlElement(name = "borrowing_record")
    private List<BorrowingRecordsSeedDto> borrowingRecordsSeedDtos;

    @NotNull
    public List<BorrowingRecordsSeedDto> getBorrowingRecordsSeedDtos() {
        return borrowingRecordsSeedDtos;
    }

    public void setBorrowingRecordsSeedDtos(List<BorrowingRecordsSeedDto> borrowingRecordsSeedDtos) {
        this.borrowingRecordsSeedDtos = borrowingRecordsSeedDtos;
    }
}
