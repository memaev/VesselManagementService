package dem.llc.vesselmanagementservice.controller;

import dem.llc.vesselmanagementservice.dto.CreateVesselRequestDto;
import dem.llc.vesselmanagementservice.dto.UpdateVesselRequestDto;
import dem.llc.vesselmanagementservice.dto.VesselDto;
import dem.llc.vesselmanagementservice.service.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vessels")
public class VesselController {

    private final VesselService vesselService;

    public VesselController(@Autowired VesselService vesselService) {
        this.vesselService = vesselService;
    }

    @PostMapping
    ResponseEntity<VesselDto> createVessel(@RequestBody CreateVesselRequestDto requestDto) {
        if (!requestDto.isValid())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        VesselDto createdVesselDto = vesselService.createVessel(requestDto);
        return (createdVesselDto == null)?ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null): ResponseEntity.status(HttpStatus.CREATED).body(createdVesselDto);
    }

    @PutMapping("/{id}")
    ResponseEntity<VesselDto> updateVessel(@PathVariable UUID id, @RequestBody UpdateVesselRequestDto updatedVesselDto) {
        if (!updatedVesselDto.isValid())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        VesselDto result = vesselService.updateVessel(id, updatedVesselDto);
        if (result == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVessel(@PathVariable UUID id) {
        boolean deletionResult = vesselService.deleteById(id);
        if (!deletionResult) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Vessel not found");
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<VesselDto> getVesselById(@PathVariable UUID id) {
        VesselDto foundVesselDto = vesselService.getVesselById(id);
        return (foundVesselDto == null)?ResponseEntity.status(HttpStatus.NOT_FOUND).body(null): ResponseEntity.ok(foundVesselDto);
    }

    // no need to return with ResponseEntity wrapper because there is no option to throw error code
    @GetMapping
    List<VesselDto> getVessels(@RequestParam(required = false) String color) {
        if (color != null && !color.isEmpty())
            return vesselService.getVesselsByColor(color);

        return vesselService.getAllVessels();
    }
}
