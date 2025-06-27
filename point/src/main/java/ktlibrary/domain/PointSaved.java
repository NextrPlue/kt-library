package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import ktlibrary.domain.*;
import ktlibrary.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PointSaved extends AbstractEvent {

    private Long id;
    private Long point;
    private Long customerId;

    public PointSaved(Point aggregate) {
        super(aggregate);
    }

    public PointSaved() {
        super();
    }
}
//>>> DDD / Domain Event
