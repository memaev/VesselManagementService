package dem.llc.vesselmanagementservice.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "vessel")
public record Vessel (
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    UUID id,

    @Column(name = "type")
    String type,

    @Column(name = "color")
    String color
) {}