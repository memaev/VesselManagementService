package dem.llc.vesselmanagementservice.service;

import dem.llc.vesselmanagementservice.dto.CreateVesselRequestDto;
import dem.llc.vesselmanagementservice.dto.VesselDto;
import dem.llc.vesselmanagementservice.model.Vessel;
import dem.llc.vesselmanagementservice.model.VesselType;
import dem.llc.vesselmanagementservice.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VesselService {

    private final VesselRepository vesselRepository;

    public VesselService(@Autowired VesselRepository vesselRepository) {
        this.vesselRepository = vesselRepository;
    }

    public VesselDto createVessel(CreateVesselRequestDto requestDto) {
        // input values cannot be empty
        if (requestDto.type().isEmpty() || requestDto.color().isEmpty())
            return null;

        // checking if vessel type is valid
        if (!VesselType.types.contains(requestDto.type()))
            return null;

        Vessel vessel = new Vessel(requestDto.type(), requestDto.color());
        Vessel createdVessel = vesselRepository.save(vessel);

        return createdVessel.toDto();
    }

    public VesselDto updateVessel(VesselDto updatedVesselDto) {
        Optional<Vessel> existedVessel = vesselRepository.findById(updatedVesselDto.id());
        if (existedVessel.isEmpty())
            return null;

        vesselRepository.save(updatedVesselDto.toModel());
        return updatedVesselDto;
    }

    public VesselDto getVesselById(UUID vesselId) {
        Optional<Vessel> foundVessel = vesselRepository.findById(vesselId);
        return foundVessel.map(Vessel::toDto).orElse(null);
    }

    public List<VesselDto> getVesselsByColor(String color) {
        return vesselRepository.findAllByColor(color).stream().map(Vessel::toDto).toList();
    }

    public List<VesselDto> getAllVessels() {
        return vesselRepository.findAll().stream().map(Vessel::toDto).toList();
    }
}
