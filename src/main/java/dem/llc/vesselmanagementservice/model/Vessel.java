package dem.llc.vesselmanagementservice.model;

import dem.llc.vesselmanagementservice.dto.VesselDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "vessel")
@NoArgsConstructor
public class Vessel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "type")
    private String type;

    @Column(name = "color")
    private String color;

    public Vessel(String type, String color) {
        this.type = type;
        this.color = color;
    }

    public VesselDto toDto() {
        return new VesselDto(id.toString(), type, color);
    }
}