package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import ktlibrary.domain.*;
import ktlibrary.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
@Getter
public class BookRequested extends AbstractEvent {

    private Long id;
    private Subsciption subsciptionId;
     private Long bookId;
    private Long bookshelfId;
    private String title;
    private Long price;

    public BookRequested(Customer aggregate) {
        super(aggregate);
    }

    public BookRequested() {
        super();
    }
    public Long getSubscriptionId() {
    return subsciptionId != null ? subsciptionId.getId() : null;
}

}
//>>> DDD / Domain Event
