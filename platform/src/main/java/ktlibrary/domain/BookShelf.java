package ktlibrary.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktlibrary.PlatformApplication;
import ktlibrary.domain.BookRead;
import lombok.Data;

@Entity
@Table(name = "BookShelf_table")
@Data
//<<< DDD / Aggregate Root
public class BookShelf {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String category;

    private Boolean isBestSeller;

    private Long viewCount;

    private String summary;

    private String coverUrl;

    private String fileUrl;

    private Long authorId;

    private Long price;

    private String authorName;

    private String introduction;

    public static BookShelfRepository repository() {
        BookShelfRepository bookShelfRepository = PlatformApplication.applicationContext.getBean(
            BookShelfRepository.class
        );
        return bookShelfRepository;
    }

    //<<< Clean Arch / Port Method
    public static void registBook(
        RegisterationRequested registerationRequested
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        BookShelf bookShelf = new BookShelf();
        repository().save(bookShelf);

        */

        /** Example 2:  finding and process
        
        // if registerationRequested.genAiId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<, Object> bookMap = mapper.convertValue(registerationRequested.getGenAiId(), Map.class);

        repository().findById(registerationRequested.get???()).ifPresent(bookShelf->{
            
            bookShelf // do something
            repository().save(bookShelf);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void readBook(ValidSubscription validSubscription) {
        //implement business logic here:

        /** Example 1:  new item 
        BookShelf bookShelf = new BookShelf();
        repository().save(bookShelf);

        BookRead bookRead = new BookRead(bookShelf);
        bookRead.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        // if validSubscription.customerId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<Long, Object> subsciptionMap = mapper.convertValue(validSubscription.getCustomerId(), Map.class);

        repository().findById(validSubscription.get???()).ifPresent(bookShelf->{
            
            bookShelf // do something
            repository().save(bookShelf);

            BookRead bookRead = new BookRead(bookShelf);
            bookRead.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void readBook(PointDeducted pointDeducted) {
        //implement business logic here:

        /** Example 1:  new item 
        BookShelf bookShelf = new BookShelf();
        repository().save(bookShelf);

        BookRead bookRead = new BookRead(bookShelf);
        bookRead.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(pointDeducted.get???()).ifPresent(bookShelf->{
            
            bookShelf // do something
            repository().save(bookShelf);

            BookRead bookRead = new BookRead(bookShelf);
            bookRead.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void isRead(BookRead bookRead) {
        //implement business logic here:

        /** Example 1:  new item 
        BookShelf bookShelf = new BookShelf();
        repository().save(bookShelf);

        */

        /** Example 2:  finding and process
        

        repository().findById(bookRead.get???()).ifPresent(bookShelf->{
            
            bookShelf // do something
            repository().save(bookShelf);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
