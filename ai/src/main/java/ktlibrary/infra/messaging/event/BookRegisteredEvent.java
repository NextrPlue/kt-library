package ktlibrary.infra.messaging.event;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRegisteredEvent {
    private Long id;
    private String manuscriptTitle;
    private String manuscriptContent;
    private Long authorId;
    private String authorName;
    private String introduction;

    private String summary;
    private String coverUrl;
    private String category;
    private String bookUrl;

    private Long price;
}
