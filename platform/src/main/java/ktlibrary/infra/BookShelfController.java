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

import ktlibrary.domain.AccessType;                        // ⬅️ Enum 정의된 위치
import org.springframework.http.ResponseEntity;            // ✅ Spring의 HTTP 응답 객체
import org.springframework.web.bind.annotation.*;   

import lombok.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/bookShelves")
@RequiredArgsConstructor
@Transactional
public class BookShelfController {
    
    private final BookShelfApplicationService bookShelfApplicationService;

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
    // @Autowired
    // BookShelfRepository bookShelfRepository;
}
//>>> Clean Arch / Inbound Adaptor
