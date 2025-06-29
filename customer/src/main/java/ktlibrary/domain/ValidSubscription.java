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
    private Long customerId; //-->> 이거 Customer에서 Long으로 바꿈???

    public ValidSubscription(Subsciption aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.isValid = aggregate.getIsValid();
        this.customerId = aggregate.getCustomerId();
    }

    public ValidSubscription() {
        super();
    }
}
//>>> DDD / Domain Event
