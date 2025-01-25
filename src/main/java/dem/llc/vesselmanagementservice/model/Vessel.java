package dem.llc.vesselmanagementservice.model;

import dem.llc.vesselmanagementservice.dto.VesselDto;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "vessel")
public class Vessel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "type")
    private String type;

    @Column(name = "color")
    private String color;

    public Vessel(UUID id, String type, String color) {
        this.id = id;
        this.type = type;
        this.color = color;
    }

    public Vessel(String type, String color) {
        this.type = type;
        this.color = color;
    }

    public Vessel() {}

    public VesselDto toDto() {
        return new VesselDto(id, type, color);
    }

    public UUID getId() {
        return this.id;
    }
}