package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class GenerateSummaryCommand {

    private Long id;
    private String manuscriptTitle;
    private String manuscriptContent;
    private Long authorId;
    private String authorName;
    private String introduction;
}
