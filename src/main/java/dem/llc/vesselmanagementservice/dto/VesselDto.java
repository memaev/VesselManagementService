package dem.llc.vesselmanagementservice.dto;

import java.util.UUID;

public record VesselDto(
        UUID id,
        String type,
        String color
) {}
