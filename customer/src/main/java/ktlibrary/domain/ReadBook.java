package ktlibrary.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "ReadBook_table")
@Data
public class ReadBook {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long bookId;
    private Long bookShelfId;
    private String title;
    private Long price;
}
