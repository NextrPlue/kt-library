package ktlibrary.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktlibrary.AuthorApplication;
import lombok.Data;

@Entity
@Table(name = "Author_table")
@Data
//<<< DDD / Aggregate Root
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    private String introduction;

    @Column(nullable = false)
    private Boolean isApproved = false;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public static AuthorRepository repository() {
        AuthorRepository authorRepository = AuthorApplication.applicationContext.getBean(
            AuthorRepository.class
        );
        return authorRepository;
    }

    //<<< Clean Arch / Port Method
    public void registerAuthor(RegisterAuthorCommand registerAuthorCommand) {
        // 작가 등록 로직
        if (registerAuthorCommand.getEmail() == null || registerAuthorCommand.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }

        if (registerAuthorCommand.getName() == null || registerAuthorCommand.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("이름은 필수입니다.");
        }

        if (repository().findByEmail(registerAuthorCommand.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        this.email = registerAuthorCommand.getEmail().trim();
        this.name = registerAuthorCommand.getName().trim();
        this.introduction = registerAuthorCommand.getIntroduction() != null ? registerAuthorCommand.getIntroduction().trim() : "";
        this.isApproved = false;

        AuthorRegistered authorRegistered = new AuthorRegistered(this);
        authorRegistered.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void approveAuthor(ApproveAuthorCommand approveAuthorCommand) {
        // 작가 승인 로직
        if (this.isApproved) {
            throw new IllegalStateException("이미 승인된 작가입니다.");
        }

        this.isApproved = true;

        AuthorApproved authorApproved = new AuthorApproved(this);
        authorApproved.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void disapproveAuthor(DisapproveAuthorCommand disapproveAuthorCommand) {
        // 작가 승인 거부 로직
        if (!this.isApproved) {
            throw new IllegalStateException("이미 승인되지 않은 상태입니다.");
        }

        this.isApproved = false;
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void editAuthor(EditAuthorCommand editAuthorCommand) {
        //implement business logic here:

        AuthorEdited authorEdited = new AuthorEdited(this);
        authorEdited.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
