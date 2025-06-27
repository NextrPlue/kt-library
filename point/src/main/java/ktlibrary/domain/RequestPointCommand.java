package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RequestPointCommand {

    private Long id;
    private Long customerId;
    private Long point;
}
