package dem.llc.vesselmanagementservice.controller;

import dem.llc.vesselmanagementservice.dto.CreateVesselRequestDto;
import dem.llc.vesselmanagementservice.dto.VesselDto;
import dem.llc.vesselmanagementservice.service.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vessel")
public class VesselController {

    @Autowired
    private VesselService vesselService;

    @GetMapping("/test")
    String getTest() {
        return "This is test";
    }

    @PostMapping("/create")
    ResponseEntity<VesselDto> createVessel(@RequestBody CreateVesselRequestDto requestDto) {
        VesselDto createdVesselDto = vesselService.createVessel(requestDto);
        return (createdVesselDto == null)?ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null): ResponseEntity.ok(createdVesselDto);
    }

    @GetMapping("/byId")
    ResponseEntity<VesselDto> getVesselById(@RequestParam UUID vesselId) {
        VesselDto foundVesselDto = vesselService.getVesselById(vesselId);
        return (foundVesselDto == null)?ResponseEntity.status(HttpStatus.NOT_FOUND).body(null): ResponseEntity.ok(foundVesselDto);
    }
}
