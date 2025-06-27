package ktlibrary.infra;

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
    BookShelfRepository bookShelfRepository;

    @StreamListener(KafkaProcessor.INPUT)
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

        // Comments //
        //도서를 등록합니다.

        // Sample Logic //
        BookShelf.registBook(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ValidSubscription'"
    )
    public void wheneverValidSubscription_ReadBook(
        @Payload ValidSubscription validSubscription
    ) {
        ValidSubscription event = validSubscription;
        System.out.println(
            "\n\n##### listener ReadBook : " + validSubscription + "\n\n"
        );

        // Comments //
        //도서를 열람합니다.

        // Sample Logic //
        BookShelf.readBook(event);
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

        // Comments //
        //도서를 열람합니다.

        // Sample Logic //
        BookShelf.readBook(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookRead'"
    )
    public void wheneverBookRead_IsRead(@Payload BookRead bookRead) {
        BookRead event = bookRead;
        System.out.println("\n\n##### listener IsRead : " + bookRead + "\n\n");

        // Comments //
        //5회 이상 열람 시 베스트셀러로 선정

        // Sample Logic //
        BookShelf.isRead(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
