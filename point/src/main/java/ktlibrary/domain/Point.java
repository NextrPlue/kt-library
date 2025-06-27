package ktlibrary.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktlibrary.PointApplication;
import ktlibrary.domain.PointDeducted;
import ktlibrary.domain.PointInsufficient;
import lombok.Data;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long point;

    private Date createAt;

    private Date updateAt;

    private Long customerId;

    public static PointRepository repository() {
        PointRepository pointRepository = PointApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    //<<< Clean Arch / Port Method
    public void requestPoint(RequestPointCommand requestPointCommand) {
        //implement business logic here:

        PointSaved pointSaved = new PointSaved(this);
        pointSaved.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void deductPoint(InvalidSubscription invalidSubscription) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        PointInsufficient pointInsufficient = new PointInsufficient(point);
        pointInsufficient.publishAfterCommit();
        PointDeducted pointDeducted = new PointDeducted(point);
        pointDeducted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        // if invalidSubscription.customerId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<Long, Object> subsciptionMap = mapper.convertValue(invalidSubscription.getCustomerId(), Map.class);

        repository().findById(invalidSubscription.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);

            PointInsufficient pointInsufficient = new PointInsufficient(point);
            pointInsufficient.publishAfterCommit();
            PointDeducted pointDeducted = new PointDeducted(point);
            pointDeducted.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void addPoint(CustomerRegistered customerRegistered) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        PointSaved pointSaved = new PointSaved(point);
        pointSaved.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        // if customerRegistered.subsciptionId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<Long, Object> customerMap = mapper.convertValue(customerRegistered.getSubsciptionId(), Map.class);

        repository().findById(customerRegistered.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);

            PointSaved pointSaved = new PointSaved(point);
            pointSaved.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
