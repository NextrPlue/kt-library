package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import ktlibrary.domain.*;
import ktlibrary.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookRead extends AbstractEvent {

    private Long id;
    private String title;
    private String category;
    private Long viewCount;
    private Boolean isBestSeller;
    private String summary;
    private String coverUrl;
    private String fileUrl;
    private Long authorId;
    private Long price;
    private String authorName;
    private String introduction;

    public BookRead(BookShelf aggregate) {
        super(aggregate);
    }

    public BookRead() {
        super();
    }
}
//>>> DDD / Domain Event
