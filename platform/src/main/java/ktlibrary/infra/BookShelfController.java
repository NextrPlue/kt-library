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

import ktlibrary.infra.BookShelfApplicationService;  // ✅ ApplicationService 위치 확인
import ktlibrary.domain.ReadBookCommand;              // ⬅️ 같이 필요

import ktlibrary.domain.BookReadModel;

import ktlibrary.domain.AccessType;                        // ⬅️ Enum 정의된 위치
import org.springframework.http.ResponseEntity;            // ✅ Spring의 HTTP 응답 객체
import org.springframework.web.bind.annotation.*;   
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
    public ResponseEntity<Map<String, Object>> readBook(@PathVariable Long bookId) {
        // 디버깅을 위해 도서 상태 확인
        BookShelf bookBefore = bookShelfRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        
        System.out.println("Before - ViewCount: " + bookBefore.getViewCount() + 
                        ", IsBestSeller: " + bookBefore.getIsBestSeller());
        
        ReadBookCommand command = ReadBookCommand.builder()
            .bookId(bookId)
            .customerId(1L)
            .accessType(AccessType.SUBSCRIPTION)
            .build();

        bookShelfApplicationService.executeBookReading(command);
        
        // 업데이트 후 상태 확인
        BookShelf bookAfter = bookShelfRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        
        System.out.println("After - ViewCount: " + bookAfter.getViewCount() + 
                        ", IsBestSeller: " + bookAfter.getIsBestSeller());
        
        // 응답 데이터 반환
        Map<String, Object> response = new HashMap<>();
        response.put("viewCount", bookAfter.getViewCount());
        response.put("isBestSeller", bookAfter.getIsBestSeller());
        
        return ResponseEntity.ok(response);
    }

    //도서상태 확인
    @GetMapping("/{bookId}")
    public ResponseEntity<BookShelf> getBook(@PathVariable Long bookId) {
        BookShelf book = bookShelfRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        return ResponseEntity.ok(book);
    }

}
//>>> Clean Arch / Inbound Adaptor
