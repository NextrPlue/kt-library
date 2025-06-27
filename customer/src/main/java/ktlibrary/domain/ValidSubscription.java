package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import ktlibrary.domain.*;
import ktlibrary.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ValidSubscription extends AbstractEvent {

    private Long id;
    private Boolean isValid;
    private CustomerId customerId;

    public ValidSubscription(Subsciption aggregate) {
        super(aggregate);
    }

    public ValidSubscription() {
        super();
    }
}
//>>> DDD / Domain Event
