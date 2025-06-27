package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import ktlibrary.domain.*;
import ktlibrary.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class BookRequested extends AbstractEvent {

    private Long id;
    private Long pointId;
    private SubsciptionId subsciptionId;

    public BookRequested(Customer aggregate) {
        super(aggregate);
    }

    public BookRequested() {
        super();
    }
}
//>>> DDD / Domain Event
