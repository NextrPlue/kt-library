package ktlibrary.infra.application.service;

import ktlibrary.domain.Book;
import ktlibrary.domain.Repository.BookRepository;
import ktlibrary.domain.PublishingRequested;
import ktlibrary.domain.StartPublishingCommand;
import ktlibrary.domain.BookNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ktlibrary.domain.PublishingStarted;
import ktlibrary.domain.Command.GenerateSummaryCommand;
import ktlibrary.infra.application.service.BookAiService;

@Service
@Transactional
public class StartPublishingService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookAiService BookAiService;

    public void handlePublishingRequest(PublishingRequested event) {
        StartPublishingCommand command = StartPublishingCommand.builder()
            .bookId(event.getId())
            .manuscriptTitle(event.getManuscriptTitle())
            .manuscriptContent(event.getManuscriptContent())
            .authorId(event.getAuthorId())
            .authorName(event.getAuthorName())
            .authorIntroduction(event.getAuthorIntroduction())
            .build();

        executePublishing(command);
    }

    private void executePublishing(StartPublishingCommand command) {

        Book book = bookRepository.findById(command.getBookId())
            .orElseThrow(() -> new BookNotFoundException(command.getBookId()));

        // 도메인 상태 변경
        book.publishingStarted(command);
        bookRepository.save(book);

        // 출간처리 시작 이벤트 발행
        PublishingStarted publishingStarted = new PublishingStarted(book);
        publishingStarted.publishAfterCommit();

        // AI 출간 파이프라인 실행
        GenerateSummaryCommand summaryCommand = new GenerateSummaryCommand();
        summaryCommand.setId(command.getBookId());
        summaryCommand.setManuscriptTitle(command.getManuscriptTitle());
        summaryCommand.setManuscriptContent(command.getManuscriptContent());
        summaryCommand.setAuthorId(command.getAuthorId());
        summaryCommand.setAuthorName(command.getAuthorName());
        summaryCommand.setIntroduction(command.getAuthorIntroduction());

        bookAiService.processBookAiPipeline(summaryCommand);
    }
}
