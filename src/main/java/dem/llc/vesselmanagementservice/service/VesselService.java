package dem.llc.vesselmanagementservice.service;

import dem.llc.vesselmanagementservice.dto.CreateVesselRequestDto;
import dem.llc.vesselmanagementservice.dto.VesselDto;
import dem.llc.vesselmanagementservice.model.Vessel;
import dem.llc.vesselmanagementservice.model.VesselType;
import dem.llc.vesselmanagementservice.repository.VesselRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VesselService {
    @Autowired
    private VesselRepository vesselRepository;

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
}
