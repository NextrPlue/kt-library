package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class EditManuscriptCommand {

    private Long id;
    private String mansuscriptTitle;
    private String maniscriptContent;
    private Long authorId;
}
