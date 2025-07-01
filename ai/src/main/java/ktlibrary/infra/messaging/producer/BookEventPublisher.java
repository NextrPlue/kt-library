package ktlibrary.infra.messaging.producer;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import ktlibrary.config.kafka.KafkaProcessor;
import ktlibrary.infra.messaging.event.BookRegisteredEvent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookEventPublisher {
    private final KafkaProcessor kafkaProcessor;

    public void publish(BookRegisteredEvent event) {
        kafkaProcessor.outboundTopic().send(
            MessageBuilder
                .withPayload(event)
                .setHeader("contentType", "application/json") // ✅ 명시
                .setHeader("type", "BookRegisteredEvent")     // ✅ 조건 기반 리스너가 쓰려면 필요
                .build()
        );
    }

    
}
