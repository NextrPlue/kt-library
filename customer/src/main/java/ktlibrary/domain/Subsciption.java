package ktlibrary.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktlibrary.CustomerApplication;
import ktlibrary.domain.InvalidSubscription;
import ktlibrary.domain.ValidSubscription;
import lombok.Data;

@Entity
@Table(name = "Subsciption_table")
@Data
//<<< DDD / Aggregate Root
public class Subsciption {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean isValid;

    private String startDate;

    private String endDate;

    private Date createdAt;

    private Date updatedAt;

    @Embedded
    private CustomerId customerId;

    public static SubsciptionRepository repository() {
        SubsciptionRepository subsciptionRepository = CustomerApplication.applicationContext.getBean(
            SubsciptionRepository.class
        );
        return subsciptionRepository;
    }

    //<<< Clean Arch / Port Method
    public void cancelSubscription(
        CancelSubscriptionCommand cancelSubscriptionCommand
    ) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void subscribe(SubscribeCommand subscribeCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void isSubscribed(BookRequested bookRequested) {
        //implement business logic here:

        /** Example 1:  new item 
        Subsciption subsciption = new Subsciption();
        repository().save(subsciption);

        ValidSubscription validSubscription = new ValidSubscription(subsciption);
        validSubscription.publishAfterCommit();
        InvalidSubscription invalidSubscription = new InvalidSubscription(subsciption);
        invalidSubscription.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        // if bookRequested.subsciptionId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<Long, Object> customerMap = mapper.convertValue(bookRequested.getSubsciptionId(), Map.class);

        repository().findById(bookRequested.get???()).ifPresent(subsciption->{
            
            subsciption // do something
            repository().save(subsciption);

            ValidSubscription validSubscription = new ValidSubscription(subsciption);
            validSubscription.publishAfterCommit();
            InvalidSubscription invalidSubscription = new InvalidSubscription(subsciption);
            invalidSubscription.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
