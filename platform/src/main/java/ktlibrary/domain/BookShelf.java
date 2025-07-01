package ktlibrary.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import ktlibrary.PlatformApplication;
import ktlibrary.domain.BecameBestseller;
import ktlibrary.domain.BookRead;
import ktlibrary.domain.BookRegistered;
import lombok.Data;

@Entity
@Table(name = "BookShelf_table")
@Data
//<<< DDD / Aggregate Root
public class BookShelf {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long bookId;

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

    public void regist(RegisterationRequested event) {
        this.bookId = event.getBookId();
        this.title = event.getTitle();
        this.category = event.getCategory();
        this.summary = event.getSummary();
        this.authorId = event.getAuthorId();
        this.authorName = event.getAuthorName();
        this.introduction = event.getIntroduction();
        this.coverUrl = event.getCoverUrl();
        this.fileUrl = event.getFileUrl();
        this.price = 1000L;
        this.viewCount = 0L;
        this.isBestSeller = false;
    }
    public BookRegistered createBookRegisteredEvent() {
        return new BookRegistered(this);  // 생성자에서 BookShelf를 넘기면 내부에서 필요한 필드 꺼내게 설계
    }

    public BookReadResult readBy(Long customerId, AccessType accessType) {

        this.viewCount += 1;

        // 베스트셀러 승격 
        boolean promoted = false;
        if (this.viewCount >= 5 && !Boolean.TRUE.equals(this.isBestSeller)) {
            this.isBestSeller = true;
            promoted = true;
        }

        // 결과 객체 반환
        return new BookReadResult(this.viewCount, promoted);
    }


}

