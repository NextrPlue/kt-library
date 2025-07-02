package ktlibrary.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import ktlibrary.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ktlibrary.BookShelfApplicationService;  // ✅ ApplicationService 위치 확인
import ktlibrary.domain.ReadBookCommand;              // ⬅️ 같이 필요

import ktlibrary.domain.BookReadModel;

import ktlibrary.domain.AccessType;                        // ⬅️ Enum 정의된 위치
import org.springframework.http.ResponseEntity;            // ✅ Spring의 HTTP 응답 객체
import org.springframework.web.bind.annotation.*;   
import lombok.*;

import java.util.List;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/bookShelves")
@RequiredArgsConstructor
@Transactional
public class BookShelfController {

    private final BookShelfApplicationService bookShelfApplicationService;
    private final BookReadRepository bookReadRepository;
    // private final BookShelfRepository bookShelfRepository;

    @GetMapping
    public List<BookReadModel> getBookshelfByCustomer(@RequestParam Long customerId) {
        return bookReadRepository.findByCustomerId(customerId);
    }

    @Autowired
    private BookShelfRepository bookShelfRepository;

    @GetMapping("/all")
    public Iterable<BookShelf> getAllBooks() {
        return bookShelfRepository.findAll();
    }

    @PostMapping("/registerTest")
    public ResponseEntity<Void> registerTestBook() {
        RegisterationRequested event = new RegisterationRequested();
        event.setBookId(100L);  // 이 값은 열람 테스트에서 쓰는 ID와 동일해야 함
        event.setTitle("테스트 도서");
        event.setCategory("문학");
        event.setAuthorId(123L);
        event.setAuthorName("홍길동");
        event.setIntroduction("테스트용");
        event.setSummary("요약");
        event.setCoverUrl("cover.jpg");
        event.setFileUrl("file.pdf");

        // BookShelf shelf = new BookShelf();
        // shelf.regist(event);
        // bookShelfRepository.save(shelf);

        bookShelfApplicationService.processRegisterBook(event);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{bookId}/read")
    public ResponseEntity<Void> readBook(@PathVariable Long bookId) {
        ReadBookCommand command = ReadBookCommand.builder()
            .bookId(bookId)
            .customerId(1L) // 테스트용
            .accessType(AccessType.SUBSCRIPTION)
            .build();

        bookShelfApplicationService.executeBookReading(command);
        return ResponseEntity.ok().build();
    }

}
//>>> Clean Arch / Inbound Adaptor
