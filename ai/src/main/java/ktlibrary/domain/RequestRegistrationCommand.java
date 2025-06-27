package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RequestRegistrationCommand {

    private Long id;
    private String summary;
    private String coverUrl;
    private String bookUrl;
    private Date createdAt;
    private Date updatedAt;
    private String manuscriptTitle;
    private String manuscriptContent;
    private Long authorId;
    private String authorName;
    private String introduction;
    private String category;
    private Long price;
}
