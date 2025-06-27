package ktlibrary.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RegisterAuthorCommand {

    private String email;
    private String name;
    private String introduction;
    private Boolean isApproved;
}
