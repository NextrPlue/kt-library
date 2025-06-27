package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import ktlibrary.domain.*;
import ktlibrary.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class InvalidSubscription extends AbstractEvent {

    private Long id;
    private Boolean isValid;
    private CustomerId customerId;

    public InvalidSubscription(Subsciption aggregate) {
        super(aggregate);
    }

    public InvalidSubscription() {
        super();
    }
}
//>>> DDD / Domain Event
