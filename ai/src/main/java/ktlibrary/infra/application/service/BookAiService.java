package ktlibrary.infra.application.service;

import ktlibrary.domain.Book;
import ktlibrary.domain.Command.GenerateCoverCommand;
import ktlibrary.domain.Command.GenerateSummaryCommand;
import ktlibrary.domain.Command.GenerateCategoryCommand;
import ktlibrary.domain.Command.RegistBookCommand;
import ktlibrary.domain.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookAiService {

    private final SummaryService summaryService;
    private final CoverService coverService;
    private final CategoryService categoryService;

    private final BookRepository bookRepository;

    // 1. 원고 요약 생성
    public String generateSummary(GenerateSummaryCommand command) {
        return summaryService.summarize(
                command.getManuscriptTitle(),
                command.getManuscriptContent(),
                command.getAuthorName(),
                command.getIntroduction()
        );
    }

    // 2. 커버 이미지 생성
    public String generateCoverImage(GenerateCoverCommand command) {
        String title = command.getManuscriptTitle();
        String summary = command.getSummary();

        return coverService.generateCoverImage(title, summary);
    }

    // 3. 카테고리 생성
    public String generateCategory(GenerateCategoryCommand command) {
        String summary = command.getSummary();

        return categoryService.generateCategory(summary);
    }

    // 4. 최종 도서 등록
    public Book registerBook(RegistBookCommand command) {
        Book book = new Book();

        book.setId(command.getId());
        book.setManuscriptTitle(command.getManuscriptTitle());
        book.setManuscriptContent(command.getManuscriptContent());
        book.setAuthorId(command.getAuthorId());
        book.setAuthorName(command.getAuthorName());
        book.setIntroduction(command.getIntroduction());

        book.setSummary(command.getSummary());
        book.setCoverUrl(command.getCoverUrl());
        book.setCategory(command.getCategory());

        return bookRepository.save(book);
    }
//
//    // 전체 파이프라인 실행
//    public void processBookAiPipeline(GenerateSummaryCommand command) {
//
//    }
}
