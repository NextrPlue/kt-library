package ktlibrary.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import ktlibrary.config.kafka.KafkaProcessor;
import ktlibrary.domain.PublishingRequested;
import ktlibrary.infra.application.service.StartPublishingService;
import ktlibrary.infra.messaging.event.BookRegisteredEvent;
import ktlibrary.domain.Repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    private StartPublishingService startPublishingService;

    // 이벤트가 발행되었는지 확인
    @StreamListener(KafkaProcessor.INPUT)
    public void testRawMessage(@Payload String raw) {
        System.out.println("📦 수신된 RAW 메시지: " + raw);

        try {
            ObjectMapper mapper = new ObjectMapper();
            BookRegisteredEvent event = mapper.readValue(raw, BookRegisteredEvent.class);
            System.out.println("✅ 수동 역직렬화 성공: " + event);
        } catch (Exception e) {
            System.out.println("❌ 역직렬화 실패: " + e.getMessage());
        }
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PublishingRequested'"
    )
    public void wheneverPublishingRequested_PublishingStarted(
        @Payload PublishingRequested publishingRequested
    ) {
        System.out.println(
            "\n\n##### listener PublishingStarted : " +
            publishingRequested + " 출간요청됨 수신 완료" +
            "\n\n"
        );

        startPublishingService.handlePublishingRequest(publishingRequested);
    }
}
