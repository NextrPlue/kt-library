package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;

@Data
@Getter
@Setter 
public class RequestBookCommand {

    private Long customerId;
    private Long subsciptionId;
    private Long bookId;
}
