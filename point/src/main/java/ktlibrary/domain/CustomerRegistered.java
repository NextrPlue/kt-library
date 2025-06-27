package ktlibrary.domain;

import java.util.*;
import ktlibrary.domain.*;
import ktlibrary.infra.AbstractEvent;
import lombok.*;

@Data
@ToString
public class CustomerRegistered extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private Boolean isKtUser;
}
