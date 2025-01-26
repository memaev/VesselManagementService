package dem.llc.vesselmanagementservice.dto;

import dem.llc.vesselmanagementservice.model.Vessel;
import dem.llc.vesselmanagementservice.model.VesselType;

import java.util.UUID;

public record VesselDto(
        UUID id,
        String type,
        String color
) {
    public Vessel toModel() {
        return new Vessel(id, type, color);
    }

    public boolean isValid() {
        return VesselType.types.contains(this.type()) && !this.type().isEmpty() && !this.color().isEmpty();
    }
}
