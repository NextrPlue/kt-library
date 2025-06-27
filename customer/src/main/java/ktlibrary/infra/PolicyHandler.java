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
    CustomerRepository customerRepository;

    @Autowired
    SubsciptionRepository subsciptionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookRequested'"
    )
    public void wheneverBookRequested_IsSubscribed(
        @Payload BookRequested bookRequested
    ) {
        BookRequested event = bookRequested;
        System.out.println(
            "\n\n##### listener IsSubscribed : " + bookRequested + "\n\n"
        );

        // Comments //
        //구독 여부를 판단합니다.

        // Sample Logic //
        Subsciption.isSubscribed(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
