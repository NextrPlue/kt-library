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
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='InvalidSubscription'"
    )
    public void wheneverInvalidSubscription_DeductPoint(
        @Payload InvalidSubscription invalidSubscription
    ) {
        InvalidSubscription event = invalidSubscription;
        System.out.println(
            "\n\n##### listener DeductPoint : " + invalidSubscription + "\n\n"
        );

        // Sample Logic //
        Point.deductPoint(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CustomerRegistered'"
    )
    public void wheneverCustomerRegistered_AddPoint(
        @Payload CustomerRegistered customerRegistered
    ) {
        CustomerRegistered event = customerRegistered;
        System.out.println(
            "\n\n##### listener AddPoint : " + customerRegistered + "\n\n"
        );

        // Sample Logic //
        Point.addPoint(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
