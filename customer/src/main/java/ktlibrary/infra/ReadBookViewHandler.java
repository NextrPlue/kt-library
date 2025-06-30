package ktlibrary.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import ktlibrary.config.kafka.KafkaProcessor;
import ktlibrary.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class ReadBookViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private ReadBookRepository readBookRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookRead_UpdateView(@Payload ReadBook event) {
    ReadBook readBook = new ReadBook();
    readBook.setBookId(event.getBookId());
    readBook.setTitle(event.getTitle());
    readBook.setBookShelfId(event.getBookShelfId());
    readBookRepository.save(readBook);
}

    //>>> DDD / CQRS
}
