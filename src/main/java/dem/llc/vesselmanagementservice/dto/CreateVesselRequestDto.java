package dem.llc.vesselmanagementservice.dto;

import dem.llc.vesselmanagementservice.util.VesselType;

public record CreateVesselRequestDto(
        String type,
        String color
) {
    public boolean isValid() {
        return VesselType.types.contains(this.type()) && !this.type().isEmpty() && !this.color().isEmpty();
    }
}
