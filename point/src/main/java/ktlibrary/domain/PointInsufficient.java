package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import ktlibrary.domain.*;
import ktlibrary.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PointInsufficient extends AbstractEvent {

    private Long id;
    private Long point;
    private Long customerId;

    public PointInsufficient(Point aggregate) {
        super(aggregate);
    }

    public PointInsufficient() {
        super();
    }
}
//>>> DDD / Domain Event
