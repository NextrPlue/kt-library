package ktlibrary.event;

import ktlibrary.domain.BookShelf;
import ktlibrary.infra.AbstractEvent;

import lombok.*;

@Data
@ToString
public class CreateBookEvent extends AbstractEvent {
    private Long id;
    private Long bookId;
    private String title;
    private String category;
    private Boolean isBestSeller;
    private Long viewCount;
    private String summary;
    private String coverUrl;
    private String fileUrl;
    private Long authorId;
    private Long price;
    private String authorName;
    private String introduction;
    
    public CreateBookEvent(BookShelf aggregate) {
        super(aggregate);
    }

    public CreateBookEvent() {
        super();
    }
}