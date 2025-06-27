package ktlibrary.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktlibrary.ManuscriptApplication;
import lombok.Data;

@Entity
@Table(name = "Manuscript_table")
@Data
//<<< DDD / Aggregate Root
public class Manuscript {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String manuscriptTitle;
    
    @Column(nullable = false)
    private String manuscriptContent;

    @Column(nullable = false)
    private Long authorId;

    private String authorName;

    private String authorIntroduction;

    @Column(updatable = false)
    private Date createdAt;

    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
    
    public static ManuscriptRepository repository() {
        ManuscriptRepository manuscriptRepository = ManuscriptApplication.applicationContext.getBean(
            ManuscriptRepository.class
        );
        return manuscriptRepository;
    }

    //<<< Clean Arch / Port Method
    public void registerManuscript(
        RegisterManuscriptCommand registerManuscriptCommand
    ) {
        //implement business logic here:
        this.authorId = registerManuscriptCommand.getAuthorId();
        this.manuscriptTitle = registerManuscriptCommand.getManuscriptTitle();
        this.manuscriptContent = registerManuscriptCommand.getManuscriptContent();

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void editManuscript(EditManuscriptCommand editManuscriptCommand) {
        this.authorId = editManuscriptCommand.getAuthorId();
        this.manuscriptTitle = editManuscriptCommand.getManuscriptTitle();
        this.manuscriptContent = editManuscriptCommand.getManuscriptContent();

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void requestPublishing(
        RequestPublishingCommand requestPublishingCommand
    ) {
        //implement business logic here:

        this.authorName = requestPublishingCommand.getAuthorName();
        this.authorIntroduction = requestPublishingCommand.getAuthorIntroduction();

        Manuscript.repository().save(this);
        PublishingRequested publishingRequested = new PublishingRequested(this);
        publishingRequested.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
