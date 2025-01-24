package dem.llc.vesselmanagementservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class VesselController {
    @GetMapping("/getTest")
    String getTest() {
        return "This is test";
    }
}
