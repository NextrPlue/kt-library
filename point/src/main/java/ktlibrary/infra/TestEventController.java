package ktlibrary.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import ktlibrary.application.PointApplicationService;
import ktlibrary.domain.*;

@RestController
@RequestMapping("/test-events")
public class TestEventController {

    @Autowired
    private PointApplicationService pointService;

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private PolicyHandler policyHandler;

    public TestEventController(PolicyHandler policyHandler, PointRepository pointRepository) {
        this.policyHandler = policyHandler;
        this.pointRepository = pointRepository;
    }

    @PostMapping("/register")
    public Point triggerCustomerRegistered(@RequestBody CustomerRegistered event) {
        policyHandler.wheneverCustomerRegistered_AddPoint(event);
        return pointRepository.findByCustomerId(event.getId())
            .orElseThrow(() -> new IllegalStateException("포인트 정보 없음"));
    }

    @PostMapping("/invalid")
    public ResponseEntity<?> triggerInvalidSubscription(@RequestBody InvalidSubscription event) {
        // 도메인 서비스에서 처리한 결과를 직접 판단
        boolean success = pointService.processInvalidSubscription(event); // ← 서비스 직접 호출

        if (!success) {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "포인트가 부족합니다.",
                "customerId", event.getCustomerId()
            ));
        }

        Point updated = pointRepository.findByCustomerId(event.getCustomerId())
            .orElseThrow(() -> new IllegalStateException("포인트 정보 없음"));
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/request")
    public Point triggerRequestPoint(@RequestBody RequestPointCommand command) {
        Point point = pointRepository.findByCustomerId(command.getCustomerId())
            .orElseThrow(() -> new IllegalStateException("포인트 정보 없음"));
        point.requestPoint(command.getPoint());
        return pointRepository.save(point);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
