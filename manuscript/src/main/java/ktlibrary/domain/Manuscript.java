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
import ktlibrary.infra.ReadAuthorRepository;

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

    private static ReadAuthorRepository readAuthorRepository() {
        return ManuscriptApplication.applicationContext.getBean(ReadAuthorRepository.class);
    }
    

    //<<< Clean Arch / Port Method
    public void registerManuscript(RegisterManuscriptCommand command) {

        // 승인 여부 확인
        ReadAuthor author = readAuthorRepository().findByAuthorId(command.getAuthorId()).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("작가 정보가 없습니다."));

        if (!author.getIsApproved()) {
            throw new IllegalStateException("승인되지 않은 작가는 원고를 등록할 수 없습니다.");
        }

        this.authorId = command.getAuthorId();
        this.manuscriptTitle = command.getManuscriptTitle();
        this.manuscriptContent = command.getManuscriptContent();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void editManuscript(EditManuscriptCommand command) {
        // 승인여부 확인
        ReadAuthor author = readAuthorRepository().findByAuthorId(command.getAuthorId()).stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("작가 정보가 없습니다."));

        if (!author.getIsApproved()) {
            throw new IllegalStateException("승인되지 않은 작가는 원고를 수정할 수 없습니다.");
        }

        this.authorId = command.getAuthorId();
        this.manuscriptTitle = command.getManuscriptTitle();
        this.manuscriptContent = command.getManuscriptContent();
    }
    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void requestPublishing(RequestPublishingCommand command) {

        // 등록된 원고가 있는지 확인
        if (this.manuscriptTitle == null || this.manuscriptTitle.trim().isEmpty()
            || this.manuscriptContent == null || this.manuscriptContent.trim().isEmpty()) {
            throw new IllegalStateException("원고가 등록되지 않았습니다. 출간 요청 불가합니다.");
        }

        this.authorName = command.getAuthorName();
        this.authorIntroduction = command.getAuthorIntroduction();

        Manuscript.repository().save(this);

        PublishingRequested publishingRequested = new PublishingRequested(this);
        publishingRequested.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
