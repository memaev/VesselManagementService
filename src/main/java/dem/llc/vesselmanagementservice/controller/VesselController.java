package dem.llc.vesselmanagementservice.controller;

import dem.llc.vesselmanagementservice.dto.CreateVesselRequestDto;
import dem.llc.vesselmanagementservice.dto.VesselDto;
import dem.llc.vesselmanagementservice.service.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vessels")
public class VesselController {

    private final VesselService vesselService;

    public VesselController(@Autowired VesselService vesselService) {
        this.vesselService = vesselService;
    }

    @GetMapping("/all")
    List<VesselDto> getAllVessels() {
        return vesselService.getAllVessels();
    }

    @PostMapping
    ResponseEntity<VesselDto> createVessel(@RequestBody CreateVesselRequestDto requestDto) {
        if (!requestDto.isValid())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        VesselDto createdVesselDto = vesselService.createVessel(requestDto);
        return (createdVesselDto == null)?ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null): ResponseEntity.ok(createdVesselDto);
    }

    @PutMapping
    ResponseEntity<VesselDto> updateVessel(@RequestBody VesselDto updatedVesselDto) {
        if (!updatedVesselDto.isValid())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        VesselDto result = vesselService.updateVessel(updatedVesselDto);
        if (result == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/byId")
    ResponseEntity<VesselDto> getVesselById(@RequestParam UUID vesselId) {
        VesselDto foundVesselDto = vesselService.getVesselById(vesselId);
        return (foundVesselDto == null)?ResponseEntity.status(HttpStatus.NOT_FOUND).body(null): ResponseEntity.ok(foundVesselDto);
    }

    // no need to return with ResponseEntity wrapper because there is no option to throw error code
    @GetMapping("/byColor")
    List<VesselDto> getVesselsByColor(@RequestParam String color) {
        return vesselService.getVesselsByColor(color);
    }

}
