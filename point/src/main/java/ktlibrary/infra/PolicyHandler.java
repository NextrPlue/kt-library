package ktlibrary.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import ktlibrary.config.kafka.KafkaProcessor;
import ktlibrary.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import ktlibrary.application.PointApplicationService;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    // @Autowired
    // PointRepository pointRepository;

    @Autowired
    private PointApplicationService pointService;

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
        // Point.deductPoint(event);
         boolean success = pointService.processInvalidSubscription(event);
        if (!success) {
            System.out.println("포인트 부족: 적립 실패 (customerId = " + event.getCustomerId() + ")");
            }
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
        // Point.addPoint(event);
        pointService.processCustomerRegistration(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
