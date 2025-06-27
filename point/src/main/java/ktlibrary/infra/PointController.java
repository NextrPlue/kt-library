package ktlibrary.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import ktlibrary.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/points")
@Transactional
public class PointController {

    @Autowired
    PointRepository pointRepository;

    @RequestMapping(
        value = "/points/requestpoint",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Point requestPoint(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody RequestPointCommand requestPointCommand
    ) throws Exception {
        System.out.println("##### /point/requestPoint  called #####");
        Point point = new Point();
        point.requestPoint(requestPointCommand);
        pointRepository.save(point);
        return point;
    }
}
//>>> Clean Arch / Inbound Adaptor
