package ktlibrary.infra;

import ktlibrary.BookShelfApplicationService;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import ktlibrary.config.kafka.KafkaProcessor;
import ktlibrary.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    private BookShelfApplicationService bookShelfService;

    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RegisterationRequested'"
    )
    public void wheneverRegisterationRequested_RegistBook(
        @Payload RegisterationRequested registerationRequested
    ) {
        RegisterationRequested event = registerationRequested;
        System.out.println(
            "\n\n##### listener RegistBook : " + registerationRequested + "\n\n"
        );

        bookShelfService.processRegisterBook(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,  // Kafka 입력
        condition = "headers['type']=='ValidSubscription'" //특정 이벤트 타입만
    )
    // validsubscription 이벤트 타입 발생-> 메서드 실행
    public void wheneverValidSubscription_ReadBook(
        @Payload ValidSubscription validSubscription
    ) {
        ValidSubscription event = validSubscription;
        System.out.println(
            "\n\n##### listener ReadBook : " + validSubscription + "\n\n"
        );

        bookShelfService.processSubscriptionReading(validSubscription);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointDeducted'"
    )
    public void wheneverPointDeducted_ReadBook(
        @Payload PointDeducted pointDeducted
    ) {
        PointDeducted event = pointDeducted;
        System.out.println(
            "\n\n##### listener ReadBook : " + pointDeducted + "\n\n"
        );

        bookShelfService.processPointReading(pointDeducted);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookRead'"
    )
    public void wheneverBookRead_IsRead(@Payload BookRead bookRead) {
        BookRead event = bookRead;
        System.out.println("\n\n##### listener IsRead : " + bookRead + "\n\n");

        bookShelfService.processBookReading(bookRead);
    }

    public void processBookReading(BookRead event) {
        // 기본적으로 아무 로직이 필요 없다면 로그만 출력해도 됨
        System.out.println("Processing BookRead event: " + event);

    }
}
//>>> Clean Arch / Inbound Adaptor
