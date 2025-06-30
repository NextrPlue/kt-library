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

    // 전체 파이프라인 실행
    public Book processBookAiPipeline(GenerateSummaryCommand command) {
        // 1. 요약 생성
        String summary = generateSummary(command);
        command.setSummary(summary);

        // 2. 커버 이미지 생성
        GenerateCoverCommand coverCommand = new GenerateCoverCommand();
        coverCommand.setId(command.getId());
        coverCommand.setManuscriptTitle(command.getManuscriptTitle());
        coverCommand.setManuscriptContent(command.getManuscriptContent());
        coverCommand.setAuthorId(command.getAuthorId());
        coverCommand.setAuthorName(command.getAuthorName());
        coverCommand.setIntroduction(command.getIntroduction());
        coverCommand.setSummary(summary);

        String coverUrl = generateCoverImage(coverCommand);
        coverCommand.setCoverUrl(coverUrl);

        // 3. 카테고리 생성
        GenerateCategoryCommand categoryCommand = new GenerateCategoryCommand();
        categoryCommand.setId(command.getId());
        categoryCommand.setManuscriptTitle(command.getManuscriptTitle());
        categoryCommand.setManuscriptContent(command.getManuscriptContent());
        categoryCommand.setAuthorId(command.getAuthorId());
        categoryCommand.setAuthorName(command.getAuthorName());
        categoryCommand.setIntroduction(command.getIntroduction());
        categoryCommand.setSummary(summary);

        String category = generateCategory(categoryCommand);
        categoryCommand.setCategory(category);

        // 4. 도서 등록
        RegistBookCommand registCommand = new RegistBookCommand();
        registCommand.setId(command.getId());
        registCommand.setManuscriptTitle(command.getManuscriptTitle());
        registCommand.setManuscriptContent(command.getManuscriptContent());
        registCommand.setAuthorId(command.getAuthorId());
        registCommand.setAuthorName(command.getAuthorName());
        registCommand.setIntroduction(command.getIntroduction());

        registCommand.setSummary(summary);
        registCommand.setCoverUrl(coverUrl);
        registCommand.setCategory(category);

        // 도서 저장
        return registerBook(registCommand);
    }
}
