package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RequestBookCommand {

    private Long id;
    private Subsciption subsciptionId;
}
