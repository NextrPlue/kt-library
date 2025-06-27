package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import ktlibrary.domain.*;
import ktlibrary.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class PointDeducted extends AbstractEvent {

    private Long id;
    private Long point;
    private Long customerId;

    public PointDeducted(Point aggregate) {
        super(aggregate);
    }

    public PointDeducted() {
        super();
    }
}
//>>> DDD / Domain Event
