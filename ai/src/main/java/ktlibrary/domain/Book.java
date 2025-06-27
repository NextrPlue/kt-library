package ktlibrary.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktlibrary.AiApplication;
import lombok.Data;

@Entity
@Table(name = "Book_table")
@Data
//<<< DDD / Aggregate Root
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String summary;

    private String coverUrl;

    private String bookUrl;

    private Date createdAt;

    private Date updatedAt;

    private String manuscriptTitle;

    private String manuscriptContent;

    private Long authorId;

    private String authorName;

    private String introduction;

    private String category;

    private Long price;

    public static BookRepository repository() {
        BookRepository bookRepository = AiApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }

    //<<< Clean Arch / Port Method
    public void requestCover(RequestCoverCommand requestCoverCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void transformEbook(TransformEbookCommand transformEbookCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void setCategory(SetCategoryCommand setCategoryCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void requestRegistration(
        RequestRegistrationCommand requestRegistrationCommand
    ) {
        //implement business logic here:

        RegisterationRequested registerationRequested = new RegisterationRequested(
            this
        );
        registerationRequested.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void calculatePrice(CalculatePriceCommand calculatePriceCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void generateSummary(GenerateSummaryCommand generateSummaryCommand) {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void publishingStarted(
        PublishingRequested publishingRequested
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Book book = new Book();
        repository().save(book);

        */

        /** Example 2:  finding and process
        

        repository().findById(publishingRequested.get???()).ifPresent(book->{
            
            book // do something
            repository().save(book);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
