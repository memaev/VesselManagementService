package dem.llc.vesselmanagementservice.service;

import dem.llc.vesselmanagementservice.dto.CreateVesselRequestDto;
import dem.llc.vesselmanagementservice.dto.UpdateVesselRequestDto;
import dem.llc.vesselmanagementservice.dto.VesselDto;
import dem.llc.vesselmanagementservice.model.Vessel;
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

        Vessel vessel = new Vessel(requestDto.type(), requestDto.color());
        Vessel createdVessel = vesselRepository.save(vessel);

        return createdVessel.toDto();
    }

    public VesselDto updateVessel(UUID id, UpdateVesselRequestDto updateRequestDto) {
        Optional<Vessel> existedVessel = vesselRepository.findById(id);
        // if there was no vessel with this id in the database
        if (existedVessel.isEmpty())
            return null;

        Vessel updatedVessel = existedVessel.get();
        updatedVessel.setType(updateRequestDto.type());
        updatedVessel.setColor(updateRequestDto.color());
        vesselRepository.save(updatedVessel);

        return updatedVessel.toDto();
    }

    public boolean deleteById(UUID id) {
        if (!vesselRepository.existsById(id))
            return false;

        vesselRepository.deleteById(id);
        return true;
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
