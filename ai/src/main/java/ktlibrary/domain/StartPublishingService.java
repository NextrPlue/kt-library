package ktlibrary.application;

import ktlibrary.domain.Book;
import ktlibrary.domain.BookRepository;
import ktlibrary.domain.PublishingRequested;
import ktlibrary.domain.StartPublishingCommand;
import ktlibrary.domain.BookNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import ktlibrary.domain.PublishingStarted;

@Service
@Transactional
public class StartPublishingService {

    @Autowired
    private BookRepository bookRepository;

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

        book.publishingStarted(command);

        bookRepository.save(book);

        // PublishingStarted publishingStarted = new PublishingStarted(book);
        // publishingStarted.publishAfterCommit();
    }
}
